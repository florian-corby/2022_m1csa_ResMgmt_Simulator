Practical Session on Simulator
Simulation of Task Scheduling

In the context of Resource Management, the subject is to develop a simple simulator for simulating
what happens when scheduling batch tasks and periodic tasks on a (parallel) platform.

The code can be written in any language (C/Java/Python/…).

The code should be versatile, to change easily its parameters.

The input (static or dynamic) of the simulator will be :

- a test file, describing the experiment to be done : the server file, the job file, the dependency
file (if any), the number of times periodic tasks will repeat, the power cap, the energy cap

- a file with a list of tasks (periodic, aperiodic) with their properties (arrival date, units of
work, deadline, period).

- a file with a list of servers (frequencies)

- a file with a list of dependencies between tasks (only used for part 3)

The schedulers to implement are :

1) Schedulers without taking dependencies into account, on one server, with only one frequency (take
the first one in the server file), with no power cap or energy cap.

- FIFO

- Round Robin

- RMS

- EDF


2) Schedulers without taking dependencies into account, with several servers, with only one
frequency, with no power cap or energy cap.

- RMS

- EDF

 3) Schedulers without taking dependencies into account and being energy-aware (several servers,
several frequencies, power and energy caps)
- FIFO (adapted to handle power/energy caps)

- Energy-aware EDF (http://www.cecs.uci.edu/technical_report/TR02-24.pdf, using CSMS or CSS, your
choice)

4) Schedulers taking dependencies into account (several servers, one frequency)
- WaVefront scheduler (as early as possible)
(https://web.archive.org/web/20160611100512/https://parasol.tamu.edu/groups/amatogroup/research/
scheduling/scheduling_algorithms/)

- CPM (Cluster Path Merge)
(https://web.archive.org/web/20160611100512/https://parasol.tamu.edu/groups/amatogroup/research/
scheduling/scheduling_algorithms/)



The output of the simulator : For each of the scheduler :

- the obtained schedule (starting time and end time for each task, on which server, at which
frequency). The format of the output file is :
\#jobID serverID starting_time ending_time frequency_used

-Metrics (the power along time with a graph, final energy of the schedule, missed deadline,
makespan)



Power model : Let f be a given frequency, and fMax be the maximum frequency of a server. We call s
the slowdown of the server when running at frequency f (e.g. s = f/fMax).

If a task runs on a server, the power is approximated by : P(s) = Pmax * s^2

We consider that Pmax = 200 Watts.

Execution model : duration of a task is w, at frequency 1. If a server is running at frequency f,
the duration is w/f.

In the source file that is requested, you must comment and argue your choices.

The final result is :

- a set of source files.

- a report with the metrics and graphs, and a table comparing the different algorithms.
