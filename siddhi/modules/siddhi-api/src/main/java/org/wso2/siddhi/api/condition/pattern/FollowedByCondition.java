/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.api.condition.pattern;

import org.wso2.siddhi.api.condition.Condition;

import java.util.List;

/**
 * 
 * FollowedByCondition class is used to specify the conditions of the pattern query
 */

public class FollowedByCondition implements Condition {

    List<Condition> followingConditions;

    /**
     * @param followingConditions List of following conditions
     */
    public FollowedByCondition(List<Condition> followingConditions) {
        this.followingConditions = followingConditions;
    }

    /**
     * get the list of following conditions
     *
     * @return list of following conditions
     */
    public List<Condition> getFollowingConditions() {
        return followingConditions;
    }
}