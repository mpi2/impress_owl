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

package org.mousephenotype.impress.individuals;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OntologysetIndividual implements IIndividual {
    
    private String selection_outcome;
    private String parameter_key;
    
    public OntologysetIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.selection_outcome = rs.getString("selection_outcome");
        this.parameter_key = rs.getString("parameter_key");
    }
    
    @Override
    public String getType() {
        if (this.selection_outcome.equals("INCREASED")) {
            return "increased-outcome-ontology-annotation-set";
        } else if (this.selection_outcome.equals("DECREASED")) {
            return "decreased-outcome-ontology-annotation-set";
        } else if (this.selection_outcome.equals("ABNORMAL")) {
            return "abnormal-outcome-ontology-annotation-set";
        } else {
            return "variable-outcome-ontology-annotation-set";
        }
    }
    
    @Override
    public String getKey() {
        return "OntologySet_" + this.selection_outcome + "_" + this.parameter_key;
    }
}
