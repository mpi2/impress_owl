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

public class PipelineIndividual extends Cohort implements IIndividual, ITitleable {
    
    public boolean is_impc;
    public String centre;
    
    public PipelineIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public final void seed(ResultSet rs) throws SQLException {
        this.id = rs.getInt("pipeline_id");
        this.key = rs.getString("pipeline_key");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.major_version = rs.getInt("major_version");
        this.minor_version = rs.getInt("minor_version");
        this.is_deprecated = rs.getBoolean("deprecated");
        this.is_impc = rs.getBoolean("impc");
        this.centre = rs.getString("centre_name");
    }
    
    @Override
    public String getTitleField() {
        return "name";
    }
    
    @Override
    public String getType() {
        if (this.key.equals("HAS_001"))
            return "unordered-pipeline";
        return "ordered-pipeline";
    }
    
    @Override
    public String getKey() {
        return "Pipeline_" + this.key;
    }
}
