import time
from work_count_down import *
from rest_count_down import *


# set time and cycles
work_time = 5
rest_time = 5
working_cycles = 3


while True:
    time.sleep(work_time)
    working_cycles -= 1
    work_count_down(work_time)
    if working_cycles == 0:
        break
    time.sleep(rest_time)
    rest_count_down(rest_time)
