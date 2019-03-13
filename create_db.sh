#!/bin/bash

# We only need the one file for creating the database
DB_CREATE_DB="sql/create_database.sql"

# By default DB_CREATE_TABLES is empty. If no table files are defined
# it will default to all table files (`ls sql/create_*_table.sql`)
# Note that this variable needs to always be an array
DB_CREATE_TABLES=()

# By default DB_CREATE_TESTS is empty. If no test files are defined
# it will default to all test files (`ls sql/test_*_data.sql`)
# Note that this variable needs to always be an array
DB_CREATE_TESTS=()

DB_FILES=(`ls sql/`)
TABLE_FILES=(`ls sql/create_*_table.sql`)

DB_USER=""
DB_PASS=""

# DB_NAME defaults to bonhto_base since its the name of the database tha
# sql/create_database.sql creates
DB_NAME="bontho_base"

function print_unknown {
    echo "Unknown option $1"
    echo "Get help with -h or --help"
}

function print_usage {
    printf \
    "Usage bash create_db.sh [OPTION]...\n\n\
    Options:\n\
    -u, --username\t define database username\n\
    -p, --password\t define database password\n\
    -c, --create-table\t define what tables you want to create\n\
                      \t can be defined multiple times\n\
    -t, --test-data\t define what test data you want to create\n\
                      \t can be defined multiple times\n\n\
    -h, --help\t\t print this usage\n"
}

function print_query_file {
    echo "Querying file: $1"
}

# Run the query for creating the database
function query_create_database {
    mysql -u $DB_USER -p$DB_PASS < $DB_CREATE_DB
}

# Run the query for creating tables
# DB_CREATE_TABLES is modified according to the commandline parameters
function query_create_tables {
    for TABLE in ${DB_CREATE_TABLES[@]}
    do
        print_query_file $TABLE
        mysql $DB_NAME -u $DB_USER -p$DB_PASS < $TABLE
    done
}

# Run the query for creating test data for tables
# DB_CREATE_TESTS is modified according to the commandline parameters
function query_test_data {
    for TEST in ${DB_CREATE_TESTS[@]}
    do
        print_query_file $TEST
        mysql $DB_NAME -u $DB_USER -p$DB_PASS < $TEST
    done
}

function query_all {
    query_create_database
    query_create_tables
    query_test_data
}

# Parse the commandline arguments
while [[ $# -gt 0 ]]
do

    key="$1"
    case $key in
        -u | --username)
        DB_USER="$2"
        shift
        shift
        ;;
        -p | --password)
        DB_PASS="$2"
        shift
        shift
        ;;
        -c | --create-table)
        # for each -c or --create-table append file to the array
        DB_CREATE_TABLES+=("sql/create_$2_table.sql")
        shift
        shift
        ;;
        -t | --test-data)
        # for each -t or --test-data append file to the array
        DB_CREATE_TESTS+=("sql/create_test_$2_data.sql")
        shift
        shift
        ;;
        -h | --help)
        print_usage
        exit 0
        ;;
        *)
        # Stop the program if any of the options are unknown
        print_unknown $1
        exit 1
        ;;
    esac
done


# Ask the database username if its not given as an argument
if [[ ! $DB_USER ]]
then
    read -p "Database user: " DB_USER
fi

# Ask the database password if its not given as an argument
if [[ ! $DB_PASS ]]
then
    # Get password as silent input
    read -sp "Database password: " DB_PASS
fi

# Use all the table files if none is defined by user
if [[ ${#DB_CREATE_TABLES[@]} -eq 0 ]]
then
    DB_CREATE_TABLES=(`ls sql/create_*_table.sql`)
fi

# Use all the test data files if none is defined by user
if [[ ${#DB_CREATE_TESTS[@]} -eq 0 ]]
then
    DB_CREATE_TESTS=(`ls sql/create_test_*_data.sql`)
fi

# Echo a empty line to make sure that the output looks clean
echo ""

# Query all the things we want
query_all

