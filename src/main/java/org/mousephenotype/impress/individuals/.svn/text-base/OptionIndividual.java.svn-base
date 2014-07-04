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

public class OptionIndividual implements IIndividual, ITitleable {

    private int option_id;
    public String name;
    public String description;
    public boolean is_default;
    public boolean is_active;
    
    public OptionIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.option_id = rs.getInt("param_option_id");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.is_default = rs.getBoolean("is_default");
        this.is_active = rs.getBoolean("is_active");
    }
    
    public int getOptionId() {
        return this.option_id;
    }
    
    @Override
    public String getType() {
        return "parameter-value-option";
    }
    
    @Override
    public String getKey() {
        return "Option_" + this.option_id;
    }

    @Override
    public String getTitleField() {
        return "name";
    }
}
