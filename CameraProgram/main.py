# Parking-System - Camera program
# Python program sample
#
# What the script does:
#   1) Reads the database credentials from a .env file
#   2) Inserts data to the local MYSQL database
#   3) Runs every 5 seconds.
#   Note: the script simulates the camera sending data to the MySQL. It is not a copy and paste
#         program. It needs to be modified to suit your needs. The idea is that after the camera algorithm has
#         determined that a car entered or left the area, the camera's internal computer will run a script like
#         this one to connect and write to the database. The code within the "main" function is what can be used.
#         The mysql query: "INSERT INTO `camera_log` (`camera_id`, `changed_spaces`) VALUES (1, -2)" is what needs
#         to be modified. For example, instead hard coding the value "-2", it should be a variable. It'll most likely
#         need a placeholder in place of the -2, and the variable somewhere else.
#         Notice that the main function reads the credentials from the .env file everytime the main function is run.
#         It may be possible to read the credentials once.
#
# What is needed to run the script:
#   1) The latest version of Python 3
#   2) PyCharm IDE
#   3) MySQL installed and configured with the parking system database.
#       - The user in the env file must be added to the parking system database
#       - Create this user in the DB and use '%' as the Limit to Hosts Matching
#       - Create another user with the same information, but using 'localhost' instead of '%'
#       - Given the INSERT privileges for the Schema parkingsystem for both users.
#
# Configuring PyCharm:
#   1) Create a new project in PyCharm with the following options:
#       - New environment using: Virtualenv
#       - Base interpreter: [choose the location of your python.exe executable]
#           For example: C:\Users\[your username]\AppData\Local\Programs\Python\Python310\python.exe
#       - Check: Create a main.py welcome script.
#       - Then, run this script (main.py) to confirm that PyCharm is working properly (You should see
#           "Hi, PyCharm" in PyCharm's terminal window).
#   2) Copy and paste the camera program code into the main.py (Delete what is in the main.py)
#   3) Install all the import packages missing (Hover your mouse pointer over any code with a red underline)
#       - For mysql.connector: Select "more actions" >> Install package mysql-connector-python
#       - For dotenv: Select "more actions" >> Install package python-dotenv
#   4) Add a Database source (This step may be skipped. Test the code without doing this step):
#       - To go: View >> Tools Windows >> Database
#       - "+" >> Data Source >> MySQL
#       - Host: localhost      <-- Note: Enter: "localhost" if your computer has the parking system DB, or enter:
#                                   [the ip address of the mysql server] when the DB is in another computer.
#       - Authentication: User & Password
#       - User: [enter mysql user from the .env file]
#       - Password: [enter password from the .env file]
#       - Save: Forever
#       - Database: parkingsystem
#       - URL: [should be automatically entered by pycharm, but it should be in the form of:
#               jdbc:mysql://localhost:3306/parkingsystem)
#       - Click on Test Connection (Troubleshoot any issues)
#       - Click Ok.
#   5) Place the .env file in the root folder of this python script
#   6) Pycharm will highlight the following code line with an error saying "Unable to resolve camera_log Table"
#       - This error can be ignored, or you can go to: View >> Tools Windows >> Database, right-click on
#           parkingsystem, and click refresh.
#   7) Run the program, and it should display the following in the terminal:
#       MySQL connection is closed
#       1 Record inserted successfully into camera_log table


import sched
import time
import mysql.connector
from dotenv import load_dotenv
import os

# initializes a general purpose event scheduler
s = sched.scheduler(time.time, time.sleep)


def main(sc):
    load_dotenv()
    connection = mysql.connector.connect(host=os.getenv("PYTHON_MYSQL_DB_URL"),
                                         database=os.getenv("PYTHON_MYSQL_DB_NAME"),
                                         user=os.getenv("PYTHON_MYSQL_DB_USERNAME"),
                                         password=os.getenv("PYTHON_MYSQL_DB_PASSWORD"))
    try:

        my_sql_insert_query = """INSERT INTO `camera_log` (`camera_id`, `changed_spaces`)
                                VALUES (1, -2)"""

        cursor = connection.cursor()
        cursor.execute(my_sql_insert_query)
        connection.commit()
        print(cursor.rowcount, "Record inserted successfully into camera_log table")
        cursor.close()

    except mysql.connector.Error as error:
        print("Failed to insert record into camera_log table {}".format(error))

    finally:
        if connection.is_connected():
            connection.close()
            print("MySQL connection is closed")

    # Run the main function after 5 seconds. sc is passed as an argument, so the main function can
    # be scheduled to run again in 5 seconds, repeating the cycle (an infinite loop).
    sc.enter(5, 1, main, (sc,))


# s.enter(delay=2s, priority=2, Function to Call=main, argument to pass to the main function=(s,)(
# which is the scheduler itself)
# Basically, schedule an event which will run the main function after 2 seconds has past. So, this line of code
# runs main once.
s.enter(2, 1, main, (s,))
s.run()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
