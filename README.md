# 7MinutesWorkout
This is a workout app that implemented based on [Udemy course](https://www.udemy.com/course/android-kotlin-developer/learn/lecture/16824538?start=15#overview). Main purpose of this application is to practice Kotlin programming skills.
\At the start page user is offered with three options, starting workout, calculationg BMI or diplaying the past exercises.  Starting the workout will open a new activity where user will be waiting for 10 seconds between each activity. Each activity is set to 30 seconds
and there are 12 activities. Each acitivity will be displayed to the user with a image of how it is done, and a counter for 30 seconds also each 
activitie's name will be anounced by the program. User can decide to exit the acitvity which in this case will reuslt in erasing the current progress 
and restarting. User will be informed about this before the exit phase via a dialog. After all 12 actvities are completed finish time of the activity 
will be added to the exercise history that can be displayed from the main page.  
\History page will display the past exercise activities to the user with respective times. All the data is stored by using Sqlite database and implementations.
\BMI page will allow user to calculate Body Mass Index by the entered values to the system. Calculations are possible on two different system, metric and
imperial. Depending on the choosen calculation system the page will differ accordingly to recieve the inputs and display them.
