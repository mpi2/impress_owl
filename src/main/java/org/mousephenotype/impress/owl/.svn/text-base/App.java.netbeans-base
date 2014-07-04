/*
 *
 * Copyright 2014 Medical Research Council Harwell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.mousephenotype.impress.owl;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.mousephenotype.impress.individuals.AtomicontologyannotationIndividual;
import org.mousephenotype.impress.individuals.CompoundontologyannotationsetIndividual;
import org.mousephenotype.impress.individuals.EntityannotationIndividual;
import org.mousephenotype.impress.individuals.IncrementIndividual;
import org.mousephenotype.impress.individuals.OntologyoptionIndividual;
import org.mousephenotype.impress.individuals.OntologyoptionsetIndividual;
import org.mousephenotype.impress.individuals.OntologysetIndividual;
import org.mousephenotype.impress.individuals.OptionIndividual;
import org.mousephenotype.impress.individuals.OptionsetIndividual;
import org.mousephenotype.impress.individuals.ParameterIndividual;
import org.mousephenotype.impress.individuals.PipelineIndividual;
import org.mousephenotype.impress.individuals.ProcedureIndividual;
import org.mousephenotype.impress.individuals.ProtocolIndividual;
import org.mousephenotype.impress.individuals.QualityannotationIndividual;
import org.mousephenotype.impress.individuals.SectionIndividual;
import org.mousephenotype.impress.objectproperties.AtomicontologyannotationObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.CompoundontologyannotationsetObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.EntityannotationObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.IncrementObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.OntologyoptionObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.OntologyoptionsetObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.OntologysetObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.OptionObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.OptionsetObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.ParameterObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.ProcedureObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.ProtocolObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.QualityannotationObjectPropertiesGenerator;
import org.mousephenotype.impress.objectproperties.SectionObjectPropertiesGenerator;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class App 
{
    protected final String iri;
    protected final String htpIri;
    protected final String outFile;
    protected final IRI ontologyIRI;
    protected final IRI documentIRI;
    protected final IRI highthroughputphenotypingIRI;
    protected OWLOntologyManager manager;
    protected Connection connection;
    protected OWLOntology ontology;
    
    public static void main( String[] args ) throws ClassNotFoundException, SQLException, OWLOntologyStorageException, OWLOntologyCreationException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException
    {
        String help = "Try: java -jar impressowl.jar /path/to/outfile.xml jdbc:mysql://username:password@localhost:3306/impress";
        if (args.length == 0 || args[0].startsWith("-h")) {
            System.out.println(help);
            System.exit(0);
        } else if (args.length != 2) {
            System.err.println("Error: Incorrect number of arguments given");
            System.out.println(help);
            System.exit(1);
        }
        
        App app = new App(args[0], args[1]);
        app.importHighThroughputPhenotypingOntology();
        app.createAnnotationPartClasses();
        app.createProtocolSectionClasses();
        app.createIndividuals();
        app.createObjectProperties();
        app.createRelationshipObjectProperties();
        app.createItemRelationships();
        app.save();
    }
    
    /**
     * Constructor
     * @param outFile The file where the output should be saved
     * @param dsn The database connection uri with username and password e.g. jdbc:mysql://username:password@localhost:3306/dbname
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public App(String outFile, String dsn) throws ClassNotFoundException, SQLException, OWLOntologyCreationException {
        //set IRIs and outfiles
        this.iri = "http://www.mousephenotype.org/impress/ontology/impress-ontology";
        this.htpIri = "http://www.mousephenotype.org/impress/ontology/high-throughput-phenotyping";
        this.outFile = outFile; //"C:\\wamp\\www\\owl\\gensubtest.owl".replace('\\', File.separatorChar);
        this.ontologyIRI = IRI.create(this.iri);
        this.documentIRI = IRI.create(new File(this.outFile));
        this.highthroughputphenotypingIRI = IRI.create(this.htpIri);
        this.manager = OWLManager.createOWLOntologyManager();
        this.manager.addIRIMapper(new SimpleIRIMapper(this.ontologyIRI, this.documentIRI));
        this.ontology = this.manager.createOntology(this.ontologyIRI);
        
        //connect to db
        //String dsn  = "jdbc:mysql://localhost:3306/impress";
        Class.forName("com.mysql.jdbc.Driver");
        String usernameAndPassword = dsn.substring(dsn.indexOf("/"), dsn.indexOf("@"));
        dsn = dsn.replaceFirst(usernameAndPassword.substring(2) + "@", "");
        String user = usernameAndPassword.substring(2, usernameAndPassword.indexOf(":")); //"root";
        String pass = usernameAndPassword.substring(usernameAndPassword.indexOf(":") + 1); //"Ops_Ram";
        connection = (Connection) DriverManager.getConnection(dsn, user, pass);
    }
    
    protected void closeConnection() {
        try {
            if (this.connection != null && ! this.connection.isClosed())
                this.connection.close();
        } catch (Exception e) {}
    }
    
    public void importHighThroughputPhenotypingOntology() throws OWLOntologyCreationException {
        OWLImportsDeclaration importsDeclaration = this.manager.getOWLDataFactory().getOWLImportsDeclaration(this.highthroughputphenotypingIRI);
        this.manager.applyChange(new AddImport(this.ontology, importsDeclaration));
        this.manager.addIRIMapper(new SimpleIRIMapper(this.highthroughputphenotypingIRI, IRI.create("file:///C:/wamp/www/owl/high-throughput-phenotyping.owl")));
        this.manager.loadOntology(this.highthroughputphenotypingIRI);
    }
    
    public void save() throws OWLOntologyStorageException {
        this.closeConnection();
        this.manager.saveOntology(this.ontology);
    }
    
    public void createAnnotationPartClasses() {
        OWLClass supEr = this.manager.getOWLDataFactory().getOWLClass(IRI.create(this.htpIri + "#single-term-annotation"));
        
        OWLClass ea = this.manager.getOWLDataFactory().getOWLClass(IRI.create(this.iri + "#entity-annotation"));
        OWLDeclarationAxiom eada = this.manager.getOWLDataFactory().getOWLDeclarationAxiom(ea);
        this.manager.addAxiom(this.ontology, eada);
        OWLSubClassOfAxiom easub = this.manager.getOWLDataFactory().getOWLSubClassOfAxiom(ea, supEr);
        this.manager.addAxiom(this.ontology, easub);
        
        OWLClass qa = this.manager.getOWLDataFactory().getOWLClass(IRI.create(this.iri + "#quality-annotation"));
        OWLDeclarationAxiom qada = this.manager.getOWLDataFactory().getOWLDeclarationAxiom(qa);
        this.manager.addAxiom(this.ontology, qada);
        OWLSubClassOfAxiom qasub = this.manager.getOWLDataFactory().getOWLSubClassOfAxiom(qa, supEr);
        this.manager.addAxiom(this.ontology, qasub);
        
        OWLDisjointClassesAxiom d = this.manager.getOWLDataFactory().getOWLDisjointClassesAxiom(ea, qa);
        this.manager.addAxiom(this.ontology, d);
    }
    
    public void createProtocolSectionClasses() throws SQLException {
        
        //Create Protocol Section class
        
        OWLClass supEr = this.manager.getOWLDataFactory().getOWLClass(IRI.create(this.iri + "#protocol-section"));
        OWLDeclarationAxiom supErDa = this.manager.getOWLDataFactory().getOWLDeclarationAxiom(supEr);
        this.manager.addAxiom(this.ontology, supErDa);
        
        OWLAnnotation titleannot = this.manager.getOWLDataFactory().getOWLAnnotation(
            this.manager.getOWLDataFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()),
            this.manager.getOWLDataFactory().getOWLLiteral("Protocol Section"));
        OWLAnnotationAssertionAxiom titleax = this.manager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(supEr.getIRI(), titleannot);
        OWLAnnotation commentannot = this.manager.getOWLDataFactory().getOWLAnnotation(
            this.manager.getOWLDataFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI()),
            this.manager.getOWLDataFactory().getOWLLiteral("A titled section of text in a protocol"));
        OWLAnnotationAssertionAxiom commentax = this.manager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(supEr.getIRI(), commentannot);
        this.manager.addAxiom(this.ontology, titleax);
        this.manager.addAxiom(this.ontology, commentax);
        
        //create the containsSection object property
        OWLObjectProperty containsSection = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(this.iri + "#containsSection"));
        OWLDeclarationAxiom containsSectionDa = this.manager.getOWLDataFactory().getOWLDeclarationAxiom(containsSection);
        this.manager.addAxiom(this.ontology, containsSectionDa);
        OWLClass protocol = this.manager.getOWLDataFactory().getOWLClass(IRI.create(this.htpIri + "#protocol"));
        OWLObjectPropertyDomainAxiom domain = this.manager.getOWLDataFactory().getOWLObjectPropertyDomainAxiom(containsSection, protocol);
        OWLObjectPropertyRangeAxiom range = this.manager.getOWLDataFactory().getOWLObjectPropertyRangeAxiom(containsSection, supEr);
        this.manager.addAxiom(this.ontology, domain);
        this.manager.addAxiom(this.ontology, range);
        
        //Create Protocol Section subclasses for headings like Purpose, Experimental Design, Equioment, etc
        
        Statement st = (Statement) this.connection.createStatement();
        String q = "SELECT id, title FROM section_title WHERE id NOT IN (6, 7) ORDER BY id";
        ResultSet rs = st.executeQuery(q);
        
        //for disjoint axiom
        List<OWLClass> sections = new ArrayList();
        
        while (rs.next()) {
            OWLClass child = this.manager.getOWLDataFactory().getOWLClass(IRI.create(this.iri + "#protocol-section_" + rs.getString("id")));
            OWLDeclarationAxiom da = this.manager.getOWLDataFactory().getOWLDeclarationAxiom(child);
            this.manager.addAxiom(this.ontology, da);
            OWLSubClassOfAxiom sub = this.manager.getOWLDataFactory().getOWLSubClassOfAxiom(child, supEr);
            this.manager.addAxiom(this.ontology, sub);
            
            OWLAnnotation annot = this.manager.getOWLDataFactory().getOWLAnnotation(
                this.manager.getOWLDataFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()),
                this.manager.getOWLDataFactory().getOWLLiteral(rs.getString("title"))
            );
            OWLAnnotationAssertionAxiom annax = this.manager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(child.getIRI(), annot);
            this.manager.addAxiom(this.ontology, annax);
            
            for (OWLClass oc : sections) {
                OWLDisjointClassesAxiom dax = this.manager.getOWLDataFactory().getOWLDisjointClassesAxiom(child, oc);
                this.manager.addAxiom(this.ontology, dax);
            }
            
            sections.add(child);
        }
    }
    
    public void createIndividuals() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        //Pipelines: create individual and assign data properties
        List<PipelineIndividual> pipelines = IndividualsBuilder.build(this.connection, "pipeline");
        for (PipelineIndividual pip : pipelines) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, pip));
        }
        
        //Procedures: create individual and assign properties
        List<ProcedureIndividual> procedures = IndividualsBuilder.build(this.connection, "procedure");
        for (ProcedureIndividual proc : procedures) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, proc));
        }
        
        //Parameters: create inividual and assign properties
        List<ParameterIndividual> parameters = IndividualsBuilder.build(this.connection, "parameter");
        for (ParameterIndividual param : parameters) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, param));
        }
        
        //Increments: create inividual and assign properties
        List<IncrementIndividual> increments = IndividualsBuilder.build(this.connection, "increment");
        for (IncrementIndividual inc : increments) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, inc));
        }
        
        //OptionSet: create optionset group
        List<OptionsetIndividual> optionsets = IndividualsBuilder.build(this.connection, "optionset");
        for (OptionsetIndividual os : optionsets) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, os));
        }
        
        //Options: create inividual and assign properties
        List<OptionIndividual> options = IndividualsBuilder.build(this.connection, "option");
        for (OptionIndividual op : options) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, op));
        }
        
        //OntologyOptionSet: create ontologyoptionset group
        List<OntologyoptionsetIndividual> ontoptionsets = IndividualsBuilder.build(this.connection, "ontologyoptionset");
        for (OntologyoptionsetIndividual os : ontoptionsets) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, os));
        }
        
        //OntologyOptions: create inividual and assign properties
        List<OntologyoptionIndividual> ontoptions = IndividualsBuilder.build(this.connection, "ontologyoption");
        for (OntologyoptionIndividual op : ontoptions) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, op));
        }
        
        //OntologySet: create ontology annotation set group
        List<OntologysetIndividual> ontset = IndividualsBuilder.build(this.connection, "ontologyset");
        for (OntologysetIndividual op : ontset) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, op));
        }
        
        //AtomicOntologyAnnotations: create mp ontology annotations
        List<AtomicontologyannotationIndividual> aos = IndividualsBuilder.build(this.connection, "atomicontologyannotation");
        for (AtomicontologyannotationIndividual ao : aos) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, ao));
        }
        
        //CompoundOntologyAnnotationSet: create container for compound Entity/Quality ontology annotations
        List<CompoundontologyannotationsetIndividual> cos = IndividualsBuilder.build(this.connection, "compoundontologyannotationset");
        for (CompoundontologyannotationsetIndividual as : cos) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, as));
        }
        
        //EntityAnnotations: create entity parts of EQ ontology annotations
        List<EntityannotationIndividual> eas = IndividualsBuilder.build(this.connection, "entityannotation");
        for (EntityannotationIndividual ea : eas) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, ea));
        }
        
        //QualityAnnotations: create quality parts of EQ ontology annotations
        List<QualityannotationIndividual> qas = IndividualsBuilder.build(this.connection, "qualityannotation");
        for (QualityannotationIndividual qa : qas) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, qa));
        }
        
        //Protocols: create individual and assign properties
        List<ProtocolIndividual> protocols = IndividualsBuilder.build(this.connection, "protocol");
        for (ProtocolIndividual protocol : protocols) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, protocol));
        }
        
        //Protocol Sections: create individual and assign properties
        List<SectionIndividual> sections = IndividualsBuilder.build(this.connection, "section");
        for (SectionIndividual section : sections) {
            this.manager.addAxioms(this.ontology, PropertiesBuilder.build(this.manager, this.iri, this.htpIri, section));
        }
    }
    
    public void createObjectProperties() throws SQLException {
        this.manager.addAxioms(this.ontology, ProcedureObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, ParameterObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, IncrementObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, OptionsetObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, OptionObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, OntologyoptionsetObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, OntologyoptionObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, OntologysetObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, AtomicontologyannotationObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, CompoundontologyannotationsetObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, EntityannotationObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, QualityannotationObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, ProtocolObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
        this.manager.addAxioms(this.ontology, SectionObjectPropertiesGenerator.build(this.connection, this.manager, this.iri, this.htpIri));
    }
    
    public void createRelationshipObjectProperties() {
        String[] relationships = {"equivalentTo", "convertibleTo", "similarTo", "differentTo", "converseTo"};
        String[] relationshipDescriptions = {
            "Equivalent means it is effectvely the same",
            "Convertible means some simple extra steps need to be taken to make the data comparable",
            "Similar indicates that a similar thing is being measured but the results are different",
            "Different means it is very much different",
            "Converse means it is measuring the opposite or converse effect"
        };
        
        List<OWLObjectProperty> rels = new ArrayList();
        
        for (int i = 0; i < relationships.length; i++) {
            OWLObjectProperty relationship = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(this.iri + "#" + relationships[i]));
            OWLDeclarationAxiom relationshipDa = this.manager.getOWLDataFactory().getOWLDeclarationAxiom(relationship);
            this.manager.addAxiom(this.ontology, relationshipDa);
            
            OWLSymmetricObjectPropertyAxiom symmetricness = this.manager.getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(relationship);
            this.manager.addAxiom(this.ontology, symmetricness);
            
            OWLAnnotation commentannot = this.manager.getOWLDataFactory().getOWLAnnotation(
                this.manager.getOWLDataFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI()),
                this.manager.getOWLDataFactory().getOWLLiteral(relationshipDescriptions[i]));
            OWLAnnotationAssertionAxiom commentax = this.manager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(relationship.getIRI(), commentannot);
            this.manager.addAxiom(this.ontology, commentax);
            
            for (OWLObjectProperty opRel : rels) {
                OWLDisjointObjectPropertiesAxiom disjointWith = this.manager.getOWLDataFactory().getOWLDisjointObjectPropertiesAxiom(opRel, relationship);
                this.manager.addAxiom(this.ontology, disjointWith);
            }
            
            rels.add(relationship);
        }
    }
    
    public void createItemRelationships() throws SQLException {
        RelationshipsBuilder.build(this.ontology, this.manager, this.connection, this.iri, "procedure");
        RelationshipsBuilder.build(this.ontology, this.manager, this.connection, this.iri, "parameter");
        RelationshipsBuilder.build(this.ontology, this.manager, this.connection, this.iri, "option");
    }
}
