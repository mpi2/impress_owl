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

package org.mousephenotype.impress.objectproperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class EntityannotationObjectPropertiesGenerator {
    public static Set<OWLAxiom> build(Connection connection, OWLOntologyManager manager, String iri, String htpIri) throws SQLException {
        Set<OWLAxiom> set = new HashSet();
        
        Statement st = (Statement) connection.createStatement();
        String q = "SELECT param_eqterm_id, 1 AS sequence FROM param_eqterm WHERE entity1_id != \"\" "
                 + "UNION "
                 + "SELECT param_eqterm_id, 2 AS sequence FROM param_eqterm WHERE entity2_id != \"\" "
                 + "UNION "
                 + "SELECT param_eqterm_id, 3 AS sequence FROM param_eqterm WHERE entity3_id != \"\" "
                 + "ORDER BY param_eqterm_id";
        ResultSet rs = st.executeQuery(q);
        
        while (rs.next()) {
            OWLObjectProperty containsAnnotationPart = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(htpIri + "#containsAnnotationPart"));
            OWLNamedIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#CompoundOntologyAnnotationSet_" + rs.getInt("param_eqterm_id")));
            OWLNamedIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#EntityAnnotation_" + rs.getInt("sequence") + "-" + rs.getInt("param_eqterm_id")));
            OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(containsAnnotationPart, subject, object);
            set.add(paa);
        }
        
        return set;
    }
}
