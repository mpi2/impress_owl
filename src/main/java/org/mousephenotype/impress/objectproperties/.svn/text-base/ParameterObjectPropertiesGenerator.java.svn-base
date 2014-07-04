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

public class ParameterObjectPropertiesGenerator {
        
    public static Set<OWLAxiom> build(Connection connection, OWLOntologyManager manager, String iri, String htpIri) throws SQLException {
        Set<OWLAxiom> set = new HashSet();
        
        Statement st = (Statement) connection.createStatement();
        String q = "SELECT pip.pipeline_key, proc.procedure_key, param.parameter_key FROM parameter param "
                 + "INNER JOIN procedure_has_parameters php1 ON php1.parameter_id = param.parameter_id "
                 + "INNER JOIN `procedure` proc ON proc.procedure_id = php1.procedure_id "
                 + "INNER JOIN pipeline_has_procedures php2 ON php2.procedure_id = proc.procedure_id "
                 + "INNER JOIN pipeline pip ON pip.pipeline_id = php2.pipeline_id "
                 + "GROUP BY pip.pipeline_id, proc.procedure_id, param.parameter_id";
        ResultSet rs = st.executeQuery(q);
        
        while (rs.next()) {
            OWLObjectProperty collectedInProcedure = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create(htpIri + "#collectedInProcedure"));
            OWLNamedIndividual subject = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Parameter_" + rs.getString("parameter_key")));
            OWLNamedIndividual object = manager.getOWLDataFactory().getOWLNamedIndividual(IRI.create(iri + "#Procedure_" + rs.getString("pipeline_key") + "-" + rs.getString("procedure_key")));
            OWLObjectPropertyAssertionAxiom paa = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(collectedInProcedure, subject, object);
            set.add(paa);
        }
        
        return set;
    }

}
