/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.examples.bpmn.receivetask;

import org.activiti.engine.impl.test.PluggableFlowableTestCase;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;

/**
 * @author Joram Barrez
 */
public class ReceiveTaskTest extends PluggableFlowableTestCase {

    @Deployment
    public void testWaitStateBehavior() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("receiveTask");
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(pi.getId())
                .activityId("waitState")
                .singleResult();
        assertNotNull(execution);

        runtimeService.trigger(execution.getId());
        assertProcessEnded(pi.getId());
    }

}
