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

public class ParameterIndividual extends Cohort implements IIndividual, ITitleable {
    
    private boolean is_increment;
    public String type;
    public String derivation;
    public boolean is_annotation;
    public boolean is_derived;
    public boolean req_data_analysis;
    public boolean is_required;
    public int increment_min;
    public String unit;
    public boolean qc_check;
    public float qc_min;
    public float qc_max;
    public String qc_notes;
    public String value_type;
    public String graph_type;
    public String data_analysis_notes;
   
    public ParameterIndividual(ResultSet rs) throws SQLException{
        this.seed(rs);
    }
    
    @Override
    public final void seed(ResultSet rs) throws SQLException{
        this.id = rs.getInt("parameter_id");
        this.key = rs.getString("parameter_key");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.major_version = rs.getInt("major_version");
        this.minor_version = rs.getInt("minor_version");
        this.is_deprecated = rs.getBoolean("deprecated");
        
        this.type = rs.getString("type");
        this.derivation = rs.getString("derivation");
        this.is_annotation = rs.getBoolean("is_annotation");
        this.is_derived = rs.getBoolean("is_derived");
        this.req_data_analysis = rs.getBoolean("is_important");
        this.is_required = rs.getBoolean("is_required");
        this.is_increment = rs.getBoolean("is_increment");
        this.increment_min = rs.getInt("increment_min");
        this.unit = rs.getString("unit");
        this.qc_check = rs.getBoolean("qc_check");
        this.qc_max = rs.getFloat("qc_max");
        this.qc_min = rs.getFloat("qc_min");
        this.qc_notes = rs.getString("qc_notes");
        this.value_type = rs.getString("value_type");
        this.graph_type = rs.getString("graph_type");
        this.data_analysis_notes = rs.getString("data_analysis_notes");
    }
    
    @Override
    public String getType() {
        if (this.type.equals("procedureMetadata")) {
            return "metadata-parameter";
        } else if (this.is_derived) {
            return "derived-parameter";
        } else if (this.is_increment ||
                   this.type.equals("seriesParameter") ||
                   this.type.equals("seriesMediaParameter")
        ) {
            return "incremental-data-parameter";
        } else {
            return "data-parameter";
        }
    }
    
    public boolean isIncrement() {
        return this.is_increment;
    }
    
    @Override
    public String getKey() {
        return "Parameter_" + this.key;
    }
    
    @Override
    public String getTitleField() {
        return "name";
    }
}
