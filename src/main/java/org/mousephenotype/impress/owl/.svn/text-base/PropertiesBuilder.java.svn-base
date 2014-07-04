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

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import org.mousephenotype.impress.individuals.IIndividual;
import org.mousephenotype.impress.individuals.ITitleable;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class PropertiesBuilder {
    
    public static Set<OWLAxiom> build(OWLOntologyManager manager, String iri, String htpIri, IIndividual individual) throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Set<OWLAxiom> set = new HashSet();
        
        IRI individualIri = IRI.create(iri + "#" + individual.getKey());
        OWLIndividual owlIndividual = manager.getOWLDataFactory().getOWLNamedIndividual(individualIri);
        OWLClass entityType = null;
        //Individuals of an IMPReSS ontology-specific Class type should use the IMPReSS iri
        if (individual.getType().equals("entity-annotation") ||
            individual.getType().equals("quality-annotation") ||
            individual.getType().startsWith("protocol-section_")
        ) {
            entityType = manager.getOWLDataFactory().getOWLClass(IRI.create(iri + "#" + individual.getType()));
        } else {
            entityType = manager.getOWLDataFactory().getOWLClass(IRI.create(htpIri + "#" + individual.getType()));
        }
        OWLClassAssertionAxiom entityClassTypeAssertion = manager.getOWLDataFactory().getOWLClassAssertionAxiom(entityType, owlIndividual);
        set.add(entityClassTypeAssertion);
        
        //loop through public fields of individual
        for (Field f : individual.getClass().getFields()) {
            IRI propertyIri = IRI.create(iri + "#" + f.getName());
            OWLDataProperty property = manager.getOWLDataFactory().getOWLDataProperty(propertyIri);
            OWL2Datatype datatype = null;
            if (f.getType().getName().endsWith("int")) {
                datatype = OWL2Datatype.XSD_INT;
            } else if (f.getType().getName().endsWith("boolean")) {
                datatype = OWL2Datatype.XSD_BOOLEAN;
            } else if (f.getType().getName().endsWith("float")) {
                datatype = OWL2Datatype.XSD_FLOAT;
            } else {
                datatype = OWL2Datatype.XSD_STRING;
            }
            
            //skip some fields in certain conditions
            if (f.getName().equals("qc_min") || f.getName().equals("qc_max") || f.getName().equals("qc_notes")) { //if qc check is off then don't include these fields
                String value = String.valueOf(Class.forName(individual.getClass().getName()).getField("qc_check").get(individual));
                if (value.equals("false"))
                    continue;
            } else if (f.getName().equals("derivation")) { //if it's not a derived parameter then don't show derived fields
                String value = String.valueOf(Class.forName(individual.getClass().getName()).getField("is_derived").get(individual));
                if (value.equals("false"))
                    continue;
            } else if (f.getName().equals("increment_min") && individual.getType().endsWith("increment-index")) { //if the increment has a defined increment index then don't show the increment_min field
                String value = String.valueOf(Class.forName(individual.getClass().getName()).getField("increment_string").get(individual));
                if (value.length() > 0)
                    continue;
            } else if (f.getName().equals("increment_string")) { //if the the increment is a simple increment then don't show the increment_string field
                String value = String.valueOf(Class.forName(individual.getClass().getName()).getField("increment_string").get(individual));
                if (value.length() == 0)
                    continue;
            } else if (f.getName().equals("increment_min") && ! individual.getType().equals("incremental-data-parameter")) { //don't include increment_min when the parameter is not incremental
                continue;
            } else if (f.getName().equals("increment_min")) { //if increment_min is 0 then don't bother with it
                String value = String.valueOf(Class.forName(individual.getClass().getName()).getField("increment_min").get(individual));
                if (value.equals("0"))
                    continue;
            }
            
            //get the value of the current field being looped over
            String value = String.valueOf(Class.forName(individual.getClass().getName()).getField(f.getName()).get(individual));

            //create Data Properties - if the property is a title field then make it an RDFS Label
            if (individual instanceof ITitleable && f.getName().equals(((ITitleable)individual).getTitleField())) {
                OWLAnnotation annot = manager.getOWLDataFactory().getOWLAnnotation(
                    manager.getOWLDataFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()),
                    manager.getOWLDataFactory().getOWLLiteral((value.equals("null")) ? "" : value)
                );
                OWLAnnotationAssertionAxiom assertion = manager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(individualIri, annot);
                set.add(assertion);
            } else {
                OWLDataPropertyRangeAxiom range = manager.getOWLDataFactory().getOWLDataPropertyRangeAxiom(property, manager.getOWLDataFactory().getOWLDatatype(datatype.getIRI()));
                OWLLiteral literal = manager.getOWLDataFactory().getOWLLiteral(((value.equals("null")) ? "" : value), datatype);
                OWLDataPropertyAssertionAxiom assertion = manager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(property, owlIndividual, literal);
                set.add(range);
                set.add(assertion);
            }
        }
        
        return set;
    }
}
