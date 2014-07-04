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

public class OntologyoptionIndividual implements IIndividual {
    
    private int id;
    public String ontology_term;
    public String ontology_id;
    public boolean is_default;
    
    public OntologyoptionIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.id = rs.getInt("param_ontologyoption_id");
        this.ontology_term = rs.getString("ontology_term");
        this.ontology_id = rs.getString("ontology_id");
        this.is_default = rs.getBoolean("is_default");
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getType() {
        return "parameter-ontology-option";
    }
    
    @Override
    public String getKey() {
        return "OntologyOption_" + this.id;
    }
}
