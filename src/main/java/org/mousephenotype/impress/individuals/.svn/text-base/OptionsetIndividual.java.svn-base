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

public class OptionsetIndividual implements IIndividual {
    
    private String parameter_key;
    
    public OptionsetIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.parameter_key = rs.getString("parameter_key");
    }
    
    public String getParameterKey() {
        return this.parameter_key;
    }
    
    @Override
    public String getType() {
        return "single-option-value-set";
    }
    
    @Override
    public String getKey() {
        return "OptionSet_" + this.parameter_key;
    }
    
}
