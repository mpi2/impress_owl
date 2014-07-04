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

public class IncrementObjectPropertiesGenerator {

    public static Set<OWLAxiom> build(Connection connection, OWLOntologyManager manager, String iri, String htpIri) throws SQLException {
        Set<OWLAxiom> set = new HashSet();
        
        Statement st = (Statement) connection.createStatement();
        String q = "SELECT p.parameter_key, i.param_increment_id AS increment_id FROM param_increment i "
                 + "INNER JOIN parameter p ON p.parameter_id = i.parameter_id "
                 + "WHERE p.deleted = 0 AND LENGTH(i.increment_string) > 0"; //only select defined increments
        ResultSet rs = st.executeQuery(q);
        
        while (rs.next()) {
            OWLObjectProperty hasIncrementIndex = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(htpIri + "#hasIncrementIndex"));
            OWLNamedIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Parameter_" + rs.getString("parameter_key")));
            OWLNamedIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Increment_" + rs.getString("increment_id")));
            OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(hasIncrementIndex, subject, object);
            set.add(paa);
        }
        
        return set;
    }

}
