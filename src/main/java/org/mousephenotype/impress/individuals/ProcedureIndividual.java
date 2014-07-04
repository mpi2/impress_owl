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

public class ProcedureIndividual extends Cohort implements IIndividual, ITitleable {
    
    private String pipeline_key;
    public String type;
    public String level;
    public String stage;
    public int min_females;
    public int min_males;
    public int min_animals;
    public boolean is_required;
    
    public ProcedureIndividual(ResultSet rs) throws SQLException{
        this.seed(rs);
    }
    
    @Override
    public final void seed(ResultSet rs) throws SQLException{
        this.id = rs.getInt("procedure_id");
        this.key = rs.getString("procedure_key");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.major_version = rs.getInt("major_version");
        this.minor_version = rs.getInt("minor_version");
        this.is_deprecated = rs.getBoolean("is_deprecated");
        
        this.pipeline_key = rs.getString("pipeline_key");
        this.type = rs.getString("procedure_type");
        this.level = rs.getString("level");
        this.stage = rs.getString("stage_label");
        this.min_females = rs.getInt("min_females");
        this.min_males = rs.getInt("min_males");
        this.min_animals = rs.getInt("min_animals");
        this.is_required = rs.getBoolean("is_mandatory");
    }
    
    @Override
    public String getType() {
        if (this.level.equals("housing"))
            return "metadata-procedure";
        return "experiment-procedure";
    }
    
    @Override
    public String getKey() {
        return "Procedure_" + this.pipeline_key + "-" + this.key;
    }
    
    public String getParentKey() {
        return "#Pipeline_" + this.pipeline_key;
    }
    
    @Override
    public String getTitleField() {
        return "name";
    }
}
