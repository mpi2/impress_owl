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

public class SectionIndividual implements IIndividual {
    
    private String procedure_key;
    private int section_type;
    private int section_id;
    public String section_text;
    
    public SectionIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.procedure_key = rs.getString("procedure_key");
        this.section_type = rs.getInt("section_type");
        this.section_id = rs.getInt("section_id");
        this.section_text = rs.getString("section_text");
    }
    
    public String getProcedureKey() {
        return this.procedure_key;
    }
    
    public int getSectionType() {
        return this.section_type;
    }
    
    public int getSectionId() {
        return this.section_id;
    }
    
    @Override
    public String getType() {
        return "protocol-section_" + this.section_type;
    }
    
    @Override
    public String getKey() {
        return "ProtocolSection_" + this.section_id;
    }
}
