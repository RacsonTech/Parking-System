import sched
import time
import mysql.connector
from dotenv import load_dotenv
import os

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

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

    sc.enter(5, 1, main, (sc,))


s.enter(2, 1, main, (s,))
s.run()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
