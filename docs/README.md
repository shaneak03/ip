# Barry User Guide

![UI Screenshot](./Ui.png)

**Meet Barry the Speedster!**  
Barry is your lightning-fast personal task manager! Inspired by the fastest hero around, Barry helps you capture, organize, and track your tasks in a flash. Whether you’re juggling deadlines, events, or daily to-dos, Barry’s intuitive interface and rapid responses ensure you stay on top of your work, no matter how fast life moves. Let Barry handle your tasks at super speed, so you can focus on what matters most!

## Adding Tasks

Add new tasks to your list in a flash! Barry supports three types of tasks: To-dos, Deadline, and Event.

### To-dos
Add a simple task with just a description.  
  
**Format:**
```
todo [task]
```
**Example:**
```
todo Buy groceries
```
**Expected output:**
```
Got it. I've added this task:
  [T][ ] Buy groceries
Now you have 1 task in the list.
```
*`[T]` indicates a To-do task.*

### Deadline
Add a task that must be done before a specific date and time.   
  
**Format:**
```
deadline [task] /by [yyyy-mm-dd HHmm]
```
**Example:**
```
deadline Submit report /by 2025-09-20 2359
```
**Expected output:**
```
Got it. I've added this task:
  [D][ ] Submit report (by: Sep 20 2025, 11:59PM)
Now you have 2 tasks in the list.
```
*`[D]` indicates a Deadline task.*

### Event
Add a task that occurs within a specific time range.  
  
**Format:**
```
event [task] /from [yyyy-mm-dd HHmm] /to [yyyy-mm-dd HHmm]
```
**Example:**
```
event Team meeting /from 2025-09-21 1400 /to 2025-09-21 1600
```
**Expected output:**
```
Got it. I've added this task:
  [E][ ] Team meeting (from: Sep 21 2025, 2:00PM to: Sep 21 2025, 4:00PM)
Now you have 3 tasks in the list.
```
*`[E]` indicates an Event task.*

## Listing Tasks
View all your current tasks at a glance.  
  
**Format:**
```
list
```
**Example:**
```
list
```
**Expected output:**
```
Here are the tasks in your list:
1. [T][ ] Buy groceries
2. [D][ ] Submit report (by: Sep 20 2025, 11:59PM)
3. [E][ ] Team meeting (from: Sep 21 2025, 2:00PM to: Sep 21 2025, 4:00PM)
```
*Each task is prefixed with two boxes: the first shows the type (`[T]` for To-do, `[D]` for Deadline, `[E]` for Event), and the second shows whether it is marked as done (`[X]`) or not (`[ ]`).*

## Marking and Unmarking Tasks
Mark a task as done or undone to keep track of your progress.  
  
**Format:**
```
mark [task_number]
unmark [task_number]
```
**Example:**
```
mark 2
unmark 2
```
**Expected output (mark):**
```
Nice! I've marked this task as done:
  [D][X] Submit report (by: Sep 20 2025, 11:59PM)
```
**Expected output (unmark):**
```
OK, I've marked this task as not done yet:
  [D][ ] Submit report (by: Sep 20 2025, 11:59PM)
```

## Deleting Tasks
Remove a task you no longer need from your list.  
  
**Format:**
```
delete [task_number]
```
**Example:**
```
delete 1
```
**Expected output:**
```
Noted. I've removed this task:
  [T][ ] Buy groceries
Now you have 2 tasks in the list.
```

## Finding Tasks
Search for tasks containing a specific keyword.  
  
**Format:**
```
find [keyword]
```
**Example:**
```
find report
```
**Expected output:**
```
Here are the matching tasks in your list:
1. [D][ ] Submit report (by: Sep 20 2025, 11:59PM)
```
  

## Viewing Schedule by Date
Display all tasks and events scheduled on or after a specific date. Deadlines on or after the date and events spanning the date will be shown.  
  
**Format:**
```
view [yyyy-mm-dd]
```
**Example:**
```
view 2025-09-21
```
**Expected output:**
```
Tasks/events scheduled for 2025-09-21:
1.[E][ ] Team meeting (from: Sep 21 2025, 2:00PM to: Sep 21 2025, 4:00PM)
```
*If no tasks/events are scheduled for the date, Barry will reply:*

```
No tasks/events scheduled for this date.
```

## Exiting Barry
Exit the application.  
  
**Format:**
```
bye
```
**Example:**
```
bye
```
**Expected output:**
```
Bye. Hope to see you again soon!
```

---

⚡️ Barry: Keeping you on track at the speed of lightning! ⚡️