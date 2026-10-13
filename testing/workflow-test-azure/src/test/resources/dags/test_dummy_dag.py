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
import time
from airflow.operators.python import PythonOperator
from airflow.utils.dates import days_ago
from airflow.operators.dummy_operator import DummyOperator

default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'schedule_interval': None,
    'start_date': days_ago(2),
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False
}



dag = DAG('test_dummy_dag', schedule_interval=None, default_args=default_args)

delay_python_task: PythonOperator = PythonOperator(task_id="delay_python_task",
                                                   dag=dag,
                                                   python_callable=lambda: time.sleep(20))

# t1, t2, t3 and t4 are examples of tasks created using operators
dummy = DummyOperator(task_id='dummy', dag=dag)

delay_python_task >> dummy