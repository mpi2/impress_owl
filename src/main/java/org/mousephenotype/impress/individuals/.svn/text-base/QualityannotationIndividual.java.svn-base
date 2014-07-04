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

public class QualityannotationIndividual implements IIndividual {
    
    private int set_id;
    public int sequence;
    public String entity_id;
    public String entity_term;
    public String option_value;
    public String increment_value;
    public String sex;
    
    public QualityannotationIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.set_id = rs.getInt("param_eqterm_id");
        this.sequence = rs.getInt("sequence");
        this.entity_id = rs.getString("quality_id");
        this.entity_term = rs.getString("quality_term");
        this.option_value = rs.getString("option_value");
        this.increment_value = rs.getString("increment_value");
        this.sex = rs.getString("sex");
    }
    
    public int getSetId() {
        return this.set_id;
    }
    
    @Override
    public String getType() {
        return "quality-annotation";
    }
    
    @Override
    public String getKey() {
        return "QualityAnnotation_" + this.sequence + "-" + this.set_id;
    }
}
