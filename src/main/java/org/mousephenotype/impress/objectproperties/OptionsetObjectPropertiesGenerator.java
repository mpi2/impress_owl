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

public class OptionsetObjectPropertiesGenerator {

    public static Set<OWLAxiom> build(Connection connection, OWLOntologyManager manager, String iri, String htpIri) throws SQLException {
        Set<OWLAxiom> set = new HashSet();
        
        Statement st = (Statement) connection.createStatement();
        String q = "SELECT parameter_key FROM parameter WHERE is_option = 1";
        ResultSet rs = st.executeQuery(q);
        
        while (rs.next()) {
            OWLObjectProperty hasDefinedValueSet = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(htpIri + "#hasDefinedValueSet"));
            OWLNamedIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Parameter_" + rs.getString("parameter_key")));
            OWLNamedIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#OptionSet_" + rs.getString("parameter_key")));
            OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(hasDefinedValueSet, subject, object);
            set.add(paa);
        }
        
        return set;
    }

}
