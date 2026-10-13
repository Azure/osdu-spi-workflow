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


from datetime import datetime
from airflow import DAG
from airflow.operators.python_operator import PythonOperator

import json

def print_hello():
 return 'Hello World'

dag = DAG(
	dag_id='${dagId}',
	description='Hello world example',
	schedule_interval=None,
	start_date=datetime(2017, 3, 20),
	catchup=False
	)

hello_operator = PythonOperator(task_id='hello_task', python_callable=print_hello, dag=dag)

hello_operator
