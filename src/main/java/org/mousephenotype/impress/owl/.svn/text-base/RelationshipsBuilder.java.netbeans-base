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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class RelationshipsBuilder {
    
    public static void build(OWLOntology ontology, OWLOntologyManager manager, Connection connection, String iri, String itemType) throws SQLException {
        
        String q = null;
        if (itemType.equals("procedure")) {
            q = "SELECT DISTINCT "
              + "	pip.pipeline_key AS subject_parent_key, "
              + "	proc.procedure_key AS subject_key, "
              + "	proc.procedure_id AS subject_id, "
              + "	r.relationship, "
              + "	ppip.pipeline_key AS object_parent_key, "
              + "	pproc.procedure_key AS object_key, "
              + "	pproc.procedure_id AS object_id "
              + "FROM procedure_relations r "
              + "JOIN pipeline_has_procedures php ON php.procedure_id = r.procedure_id "
              + "JOIN `procedure` proc ON proc.procedure_id = php.procedure_id "
              + "JOIN pipeline pip ON pip.pipeline_id = php.pipeline_id "
              + "JOIN pipeline_has_procedures pphp ON pphp.procedure_id = r.parent_id "
              + "JOIN `procedure` pproc ON pproc.procedure_id = pphp.procedure_id "
              + "JOIN pipeline ppip ON ppip.pipeline_id = pphp.pipeline_id";
        } else if (itemType.equals("parameter")) {
            q = "SELECT parameter_key AS subject_key, relationship, parent_key AS object_key FROM parameter_relations";
        } else if (itemType.equals("option")) {
            q = "SELECT param_option_id as subject_id, relationship, parent_id AS object_id FROM param_option_relations";
        }
        
        Statement st = (Statement) connection.createStatement();
        ResultSet rs = st.executeQuery(q);
        
        if (itemType.equals("procedure")) {
            while (rs.next()) {
                OWLIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Procedure_" + rs.getString("subject_parent_key") + "-" + rs.getString("subject_key")));
                OWLIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Procedure_" + rs.getString("object_parent_key") + "-" + rs.getString("object_key")));
                OWLObjectProperty property = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(iri + "#" + RelationshipsBuilder.convertRelationship(rs.getString("relationship"))));
                OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
                manager.addAxiom(ontology, paa);
            }
        } else if (itemType.equals("parameter")) {
            while(rs.next()) {
                OWLIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Parameter_" + rs.getString("subject_key")));
                OWLIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Parameter_" + rs.getString("object_key")));
                OWLObjectProperty property = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(iri + "#" + RelationshipsBuilder.convertRelationship(rs.getString("relationship"))));
                OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
                manager.addAxiom(ontology, paa);
            }
        } else if (itemType.equals("option")) {
            while (rs.next()) {
                OWLIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Option_" + rs.getString("subject_id")));
                OWLIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Option_" + rs.getString("object_id")));
                OWLObjectProperty property = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(iri + "#" + RelationshipsBuilder.convertRelationship(rs.getString("relationship"))));
                OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
                manager.addAxiom(ontology, paa);
            }
        }
    }
    
    protected static String convertRelationship(String relationship) {
        return relationship.toLowerCase() + "To";
    }
    
}
