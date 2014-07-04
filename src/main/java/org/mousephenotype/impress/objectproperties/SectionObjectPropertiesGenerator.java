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

public class SectionObjectPropertiesGenerator {
    public static Set<OWLAxiom> build(Connection connection, OWLOntologyManager manager, String iri, String htpIri) throws SQLException {
        Set<OWLAxiom> set = new HashSet();
        
        Statement st = (Statement) connection.createStatement();
        String q = "SELECT section_id, procedure_key FROM section "
                 + "INNER JOIN section_title st ON st.id = section.section_title_id "
                 + "INNER JOIN sop ON sop.sop_id = section.sop_id "
                 + "INNER JOIN `procedure` p ON p.procedure_id = sop.procedure_id "
                 + "ORDER BY p.procedure_id, sop.sop_id, st.weight";
        ResultSet rs = st.executeQuery(q);
        
        while (rs.next()) {
            OWLObjectProperty containsSection = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(iri + "#containsSection"));
            OWLNamedIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Protocol_" + rs.getString("procedure_key")));
            OWLNamedIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#ProtocolSection_" + rs.getInt("section_id")));
            OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(containsSection, subject, object);
            set.add(paa);
        }
        
        return set;
    }

}
