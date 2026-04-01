#!/bin/bash
# ===============================================================================
#                    QUICK COMMANDS - API REST Rendez-vous
# ===============================================================================
PROJECT_PATH="/home/oulh/workspace/back-api"
API_URL="http://localhost:8080"
# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color
# ===============================================================================
# BUILD COMMANDS
# ===============================================================================
build_compile() {
    echo -e "${BLUE}[BUILD]${NC} Compiling project..."
    cd "$PROJECT_PATH" && mvn clean compile
}
build_package() {
    echo -e "${BLUE}[BUILD]${NC} Building JAR package..."
    cd "$PROJECT_PATH" && mvn clean package
}
build_install() {
    echo -e "${BLUE}[BUILD]${NC} Installing dependencies..."
    cd "$PROJECT_PATH" && mvn clean install
}
# ===============================================================================
# RUN COMMANDS
# ===============================================================================
run_app() {
    echo -e "${GREEN}[RUN]${NC} Starting Spring Boot application..."
    cd "$PROJECT_PATH" && mvn spring-boot:run
}
run_test() {
    echo -e "${GREEN}[TEST]${NC} Running tests..."
    cd "$PROJECT_PATH" && mvn test
}
# ===============================================================================
# API TEST COMMANDS
# ===============================================================================
test_health() {
    echo -e "${YELLOW}[API]${NC} Testing health endpoint..."
    curl -X GET "$API_URL/api/appointments/health"
    echo ""
}
test_create() {
    echo -e "${YELLOW}[API]${NC} Creating a new appointment..."
    curl -X POST "$API_URL/api/appointments" \
      -H "Content-Type: application/json" \
      -d '{
        "clientName": "Jean Dupont",
        "clientEmail": "jean.dupont@example.com",
        "dateTime": "2024-03-20T14:30:00",
        "description": "Consultation générale"
      }'
    echo ""
}
test_read_all() {
    echo -e "${YELLOW}[API]${NC} Reading all appointments..."
    curl -X GET "$API_URL/api/appointments" | jq .
    echo ""
}
test_read_one() {
    local id=${1:-1}
    echo -e "${YELLOW}[API]${NC} Reading appointment ID: $id..."
    curl -X GET "$API_URL/api/appointments/$id" | jq .
    echo ""
}
test_update() {
    local id=${1:-1}
    echo -e "${YELLOW}[API]${NC} Updating appointment ID: $id..."
    curl -X PUT "$API_URL/api/appointments/$id" \
      -H "Content-Type: application/json" \
      -d '{
        "clientName": "Jean Dupont",
        "clientEmail": "jean.dupont@example.com",
        "dateTime": "2024-03-20T16:00:00",
        "description": "Consultation générale - Mise à jour"
      }' | jq .
    echo ""
}
test_delete() {
    local id=${1:-1}
    echo -e "${YELLOW}[API]${NC} Deleting appointment ID: $id..."
    curl -X DELETE "$API_URL/api/appointments/$id" -v
    echo ""
}
test_search_email() {
    local email=${1:-"jean.dupont@example.com"}
    echo -e "${YELLOW}[API]${NC} Searching appointments by email: $email..."
    curl -X GET "$API_URL/api/appointments/client/$email" | jq .
    echo ""
}
test_validation_error() {
    echo -e "${YELLOW}[API]${NC} Testing validation error (invalid email)..."
    curl -X POST "$API_URL/api/appointments" \
      -H "Content-Type: application/json" \
      -d '{
        "clientName": "Test",
        "clientEmail": "invalid-email",
        "dateTime": "2024-03-20T14:30:00"
      }' | jq .
    echo ""
}
# ===============================================================================
# DOCUMENTATION COMMANDS
# ===============================================================================
show_readme() {
    echo -e "${BLUE}[DOC]${NC} Opening README.md..."
    cat "$PROJECT_PATH/README.md" | less
}
show_api_examples() {
    echo -e "${BLUE}[DOC]${NC} Opening API_EXAMPLES.md..."
    cat "$PROJECT_PATH/API_EXAMPLES.md" | less
}
show_summary() {
    echo -e "${BLUE}[DOC]${NC} Opening IMPLEMENTATION_SUMMARY.md..."
    cat "$PROJECT_PATH/IMPLEMENTATION_SUMMARY.md" | less
}
# ===============================================================================
# UTILITY COMMANDS
# ===============================================================================
show_help() {
    cat << HELP
Usage: ./QUICK_COMMANDS.sh [COMMAND]
BUILD COMMANDS:
  build-compile        Compile the project
  build-package        Build JAR package
  build-install        Install dependencies
RUN COMMANDS:
  run-app              Start Spring Boot application
  run-test             Run unit tests
API TEST COMMANDS:
  test-health          Test health endpoint
  test-create          Create a new appointment
  test-read-all        Read all appointments
  test-read-one [id]   Read one appointment by ID
  test-update [id]     Update an appointment
  test-delete [id]     Delete an appointment
  test-search [email]  Search appointments by email
  test-validation      Test validation error
DOCUMENTATION:
  show-readme          Display README.md
  show-api-examples    Display API_EXAMPLES.md
  show-summary         Display IMPLEMENTATION_SUMMARY.md
UTILITY:
  help                 Show this help message
  all-tests            Run all API tests sequentially
EXAMPLES:
  ./QUICK_COMMANDS.sh build-compile
  ./QUICK_COMMANDS.sh run-app
  ./QUICK_COMMANDS.sh test-health
  ./QUICK_COMMANDS.sh test-read-one 1
  ./QUICK_COMMANDS.sh all-tests
HELP
}
run_all_tests() {
    echo -e "${GREEN}========== RUNNING ALL API TESTS ==========${NC}"
    test_health
    sleep 1
    test_create
    sleep 1
    test_read_all
    sleep 1
    test_read_one 1
    sleep 1
    test_search_email "jean.dupont@example.com"
    echo -e "${GREEN}========== ALL TESTS COMPLETED ==========${NC}"
}
# ===============================================================================
# MAIN EXECUTION
# ===============================================================================
case "$1" in
    # Build commands
    build-compile)
        build_compile
        ;;
    build-package)
        build_package
        ;;
    build-install)
        build_install
        ;;
    # Run commands
    run-app)
        run_app
        ;;
    run-test)
        run_test
        ;;
    # API test commands
    test-health)
        test_health
        ;;
    test-create)
        test_create
        ;;
    test-read-all)
        test_read_all
        ;;
    test-read-one)
        test_read_one "$2"
        ;;
    test-update)
        test_update "$2"
        ;;
    test-delete)
        test_delete "$2"
        ;;
    test-search)
        test_search_email "$2"
        ;;
    test-validation)
        test_validation_error
        ;;
    # Documentation
    show-readme)
        show_readme
        ;;
    show-api-examples)
        show_api_examples
        ;;
    show-summary)
        show_summary
        ;;
    # Utility
    help|--help|-h)
        show_help
        ;;
    all-tests)
        run_all_tests
        ;;
    *)
        echo "Unknown command: $1"
        echo "Use 'help' for usage information"
        show_help
        exit 1
        ;;
esac
