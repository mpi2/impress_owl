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

public class AtomicontologyannotationIndividual implements IIndividual {
    
    private String parameter_key;
    private String selection_outcome;
    private int id;
    public String ontology_id;
    public String ontology_term;
    public String sex;
    public String option_value;
    public String increment_value;
    
    public AtomicontologyannotationIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.id = rs.getInt("param_mpterm_id");
        this.parameter_key = rs.getString("parameter_key");
        this.selection_outcome = rs.getString("selection_outcome");
        this.ontology_id = rs.getString("mp_id");
        this.ontology_term = rs.getString("mp_term");
        this.sex = rs.getString("sex");
        this.option_value = rs.getString("option_value");
        this.increment_value = rs.getString("increment_value");
    }
    
    public String getParameterKey() {
        return this.parameter_key;
    }
    
    public String getSelectionOutcome() {
        return this.selection_outcome;
    }
    
    @Override
    public String getType() {
        return "single-term-annotation";
    }
    
    @Override
    public String getKey() {
        return "AtomicOntologyAnnotation_" + this.id;
    }

}
