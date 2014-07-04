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

/*
 * This class is only for defined increments with an increment_string. Simple repeat
 * increments don't have a class or individual created for them. The is_increment property
 * on the ParameterIndividual is all that is used to indicate that
 */
public class IncrementIndividual implements IIndividual {

    private int increment_id;
    private int parameter_id;
    public String increment_string;
    public String increment_type;
    public String increment_unit;
    public int increment_min;
    
    public IncrementIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.increment_id = rs.getInt("param_increment_id");
        this.parameter_id = rs.getInt("parameter_id");
        this.increment_string = rs.getString("increment_string");
        this.increment_type = rs.getString("increment_type");
        this.increment_unit = rs.getString("increment_unit");
        this.increment_min = rs.getInt("increment_min");
    }
    
    public int getParameterId() {
        return this.parameter_id;
    }
    
    @Override
    public String getType() {
        return "mandatory-increment-index";
    }
    
    @Override
    public String getKey() {
        return "Increment_" + this.increment_id;
    }
}
