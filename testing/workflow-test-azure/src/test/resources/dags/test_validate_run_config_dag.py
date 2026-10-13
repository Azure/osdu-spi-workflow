# Copyright © Microsoft Corporation
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


from airflow import DAG
from datetime import datetime, timedelta
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import BranchPythonOperator
from airflow.utils.dates import days_ago

import json

# Following are defaults which can be overridden later on
default_args = {
  'owner': 'airflow',
  'depends_on_past': False,
  'start_date': days_ago(2),
  'schedule_interval': None,
  'email': ['airflow@example.com'],
  'email_on_failure': False,
  'email_on_retry': False,
  'retries': 1,
  'retry_delay': timedelta(minutes=1)
}

MANDATORY_CONFIGURATION_EXPECTED = ["run_id", "workflow_name", "correlation_id", "execution_context", "authToken"]

dag = DAG('${dagId}', schedule_interval=None, default_args=default_args)

def check_for_expected_configuration(**context):
  dag_conf = context["dag_run"].conf
  is_mandatory_config_present = True
  if dag_conf:
    for mandatory_conf_key in MANDATORY_CONFIGURATION_EXPECTED:
      if mandatory_conf_key not in dag_conf:
        is_mandatory_config_present = False
        break
  else:
    is_mandatory_config_present = False

  if is_mandatory_config_present:
    return "success_trigger"
  else:
    return "failure_trigger"

branch_task = BranchPythonOperator(task_id='check_for_expected_config', python_callable=check_for_expected_configuration, dag=dag, provide_context=True)

success_task = BashOperator(
   task_id='success_trigger',
   bash_command='exit 0',
   dag=dag,
)

failure_task = BashOperator(
  task_id='failure_trigger',
  bash_command='exit 1',
  dag=dag,
)

branch_task >> [success_task, failure_task]
