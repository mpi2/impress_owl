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

package org.mousephenotype.impress.owl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.mousephenotype.impress.individuals.IIndividual;

public class IndividualsBuilder {
    
    public static List build(Connection connection, String type) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        List list = new ArrayList();
        Statement st = (Statement) connection.createStatement();
        String q = null;
        
        if (type.equals("pipeline")) {
            q = "SELECT * FROM pipeline WHERE deleted = 0 ORDER BY weight";
        } else if (type.equals("procedure")) {
            q = "SELECT pip.pipeline_key, type.key as procedure_type, stage.label AS stage_label, proc.*, php.* "
              + "FROM `procedure` proc "
              + "INNER JOIN pipeline_has_procedures php ON proc.procedure_id = php.procedure_id "
              + "INNER JOIN pipeline pip ON pip.pipeline_id = php.pipeline_id "
              + "INNER JOIN procedure_week stage ON stage.id = php.week "
              + "INNER JOIN procedure_type type ON proc.type = type.id "
              + "WHERE php.is_deleted = 0 AND php.is_internal = 0 "
              + "ORDER BY pip.weight, php.weight";
        } else if (type.equals("parameter")) {
            q = "SELECT i.increment_min, u.unit, p.* FROM parameter p "
              + "INNER JOIN units u ON u.id = p.unit "
              + "LEFT JOIN param_increment i ON i.parameter_id = p.parameter_id "
              + "AND i.increment_type = \"repeat\" "
              + "AND i.increment_min >= 1 "
              + "AND i.increment_string = \"\"";
        } else if (type.equals("increment")) {
            q = "SELECT * FROM param_increment WHERE deleted = 0 AND LENGTH(increment_string) > 0"; //only select defined increments to create individuals for
        } else if (type.equals("optionset")) {
            q = "SELECT parameter_key FROM parameter WHERE is_option = 1 AND deleted = 0";
        } else if (type.equals("ontologyoptionset")) {
            q = "SELECT p.parameter_key FROM parameter p "
              + "INNER JOIN parameter_has_ontologygroups pho ON pho.parameter_id = p.parameter_id "
              + "GROUP BY p.parameter_id";
        } else if (type.equals("option")) {
            q = "SELECT o.* FROM param_option o "
              + "INNER JOIN parameter_has_options pho ON pho.param_option_id = o.param_option_id "
              + "INNER JOIN parameter p ON p.parameter_id = pho.parameter_id "
              + "WHERE p.deleted = 0 AND o.deleted = 0 " 
              + "GROUP BY o.param_option_id";
        } else if (type.equals("ontologyoption")) {
            q = "SELECT o.* FROM param_ontologyoption o "
              + "INNER JOIN ontology_group g ON g.ontology_group_id = o.ontology_group_id "
              + "INNER JOIN parameter_has_ontologygroups pho ON pho.ontology_group_id = g.ontology_group_id "
              + "INNER JOIN parameter p ON p.parameter_id = pho.parameter_id "
              + "WHERE p.deleted = 0 AND o.deleted = 0 "
              + "GROUP BY o.param_ontologyoption_id";
        } else if (type.equals("ontologyset")) {
            q = "SELECT p.parameter_key, mp.selection_outcome FROM parameter p "
              + "INNER JOIN param_mpterm mp ON mp.parameter_id = p.parameter_id "
              + "GROUP BY p.parameter_id, mp.selection_outcome "
              + "UNION "
              + "SELECT p.parameter_key, eq.selection_outcome FROM parameter p "
              + "INNER JOIN param_eqterm eq ON eq.parameter_id = p.parameter_id "
              + "GROUP BY p.parameter_id, eq.selection_outcome";
        } else if (type.equals("atomicontologyannotation")) {
            q = "SELECT DISTINCT p.parameter_key, mp.*, o.name AS option_value, i.increment_string AS increment_value FROM parameter p "
              + "INNER JOIN param_mpterm mp ON mp.parameter_id = p.parameter_id "
              + "LEFT JOIN parameter_has_options pho ON pho.parameter_id = p.parameter_id "
              + "LEFT JOIN param_option o ON o.param_option_id = pho.param_option_id "
              + "LEFT JOIN param_increment i ON i.parameter_id = p.parameter_id "
              + "GROUP BY p.parameter_id, mp.selection_outcome, mp.param_mpterm_id";
        } else if (type.equals("compoundontologyannotationset")) {
            q = "SELECT eq.param_eqterm_id AS id FROM param_eqterm eq "
              + "INNER JOIN parameter p ON p.parameter_id = eq.parameter_id "
              + "WHERE p.deleted = 0";
        } else if (type.equals("entityannotation")) {
            q = "SELECT param_eqterm_id, 1 AS sequence, entity1_id AS entity_id, entity1_term AS entity_term, o.name AS option_value, i.increment_string AS increment_value, sex FROM param_eqterm "
              + "LEFT JOIN param_option o ON o.param_option_id = option_id "
              + "LEFT JOIN param_increment i ON i.param_increment_id = increment_id "
              + "WHERE entity1_id != \"\" "
              + "UNION "
              + "SELECT param_eqterm_id, 2 AS sequence, entity2_id AS entity_id, entity2_term AS entity_term, o.name AS option_value, i.increment_string AS increment_value, sex FROM param_eqterm "
              + "LEFT JOIN param_option o ON o.param_option_id = option_id "
              + "LEFT JOIN param_increment i ON i.param_increment_id = increment_id "
              + "WHERE entity2_id != \"\" "
              + "UNION "
              + "SELECT param_eqterm_id, 3 AS sequence, entity3_id AS entity_id, entity3_term AS entity_term, o.name AS option_value, i.increment_string AS increment_value, sex FROM param_eqterm "
              + "LEFT JOIN param_option o ON o.param_option_id = option_id "
              + "LEFT JOIN param_increment i ON i.param_increment_id = increment_id "
              + "WHERE entity3_id != \"\" "
              + "ORDER BY param_eqterm_id";
        } else if (type.equals("qualityannotation")) {
            q = "SELECT param_eqterm_id, 1 AS sequence, quality1_id AS quality_id, quality1_term AS quality_term, o.name AS option_value, i.increment_string AS increment_value, sex FROM param_eqterm "
              + "LEFT JOIN param_option o ON o.param_option_id = option_id "
              + "LEFT JOIN param_increment i ON i.param_increment_id = increment_id "
              + "WHERE quality1_id != \"\" "
              + "UNION "
              + "SELECT param_eqterm_id, 2 AS sequence, quality2_id AS quality_id, quality2_term AS quality_term, o.name AS option_value, i.increment_string AS increment_value, sex FROM param_eqterm "
              + "LEFT JOIN param_option o ON o.param_option_id = option_id "
              + "LEFT JOIN param_increment i ON i.param_increment_id = increment_id "
              + "WHERE quality2_id != \"\" "
              + "ORDER BY param_eqterm_id";
        } else if (type.equals("protocol")) {
            q = "SELECT proc.procedure_key, s.title FROM sop s "
              + "INNER JOIN `procedure` proc ON proc.procedure_id = s.procedure_id "
              + "INNER JOIN pipeline_has_procedures php ON php.procedure_id = proc.procedure_id "
              + "INNER JOIN pipeline pip ON pip.pipeline_id = php.pipeline_id "
              + "WHERE php.is_deleted = 0 AND pip.deleted = 0 "
              + "ORDER BY pip.weight, php.weight";
        } else if (type.equals("section")) {
            q = "SELECT section_id, procedure_key, section_title_id AS section_type, section_text FROM section "
              + "INNER JOIN section_title st ON st.id = section.section_title_id "
              + "INNER JOIN sop ON sop.sop_id = section.sop_id "
              + "INNER JOIN `procedure` p ON p.procedure_id = sop.procedure_id "
              + "ORDER BY p.procedure_id, sop.sop_id, st.weight";
        }
        
        ResultSet rs = st.executeQuery(q);
        String className = "org.mousephenotype.impress.individuals." + String.valueOf(type.charAt(0)).toUpperCase() + type.substring(1) + "Individual";
        Class c = Class.forName(className);
        Constructor con = c.getConstructor(ResultSet.class);
        
        while (rs.next()) {
            list.add((IIndividual) con.newInstance(rs));
        }
        
        return list;
    }
}
