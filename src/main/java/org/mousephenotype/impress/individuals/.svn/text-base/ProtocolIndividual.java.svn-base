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

public class ProtocolIndividual implements IIndividual, ITitleable {

    private String procedure_key;
    public String title;
    
    public ProtocolIndividual(ResultSet rs) throws SQLException {
        this.seed(rs);
    }
    
    @Override
    public void seed(ResultSet rs) throws SQLException {
        this.procedure_key = rs.getString("procedure_key");
        this.title = rs.getString("title");
    }
    
    public String getProcedureKey() {
        return this.procedure_key;
    }
    
    @Override
    public String getType() {
        return "protocol";
    }
    
    @Override
    public String getKey() {
        return "Protocol_" + this.procedure_key;
    }

    @Override
    public String getTitleField() {
        return "title";
    }
}
