package utils;

import pages.*;

public class PageInitializer {

    public static LoginPage loginPage;
    public static DashboardPage dashboardPage;
    public static AddEmployeePage addEmployeePage;
    public static AddUsernamePage addUsernamePage;
    public static AddDependantsPage addDependantsPage;
    public static AddProfilePicturePage addProfilePicturePage;
    public static SearchEmployeePage searchEmployeePage;
    public static UpdateContactDetailPage updateContactDetailPage;
    public static UpdatePersonalDetailPage updatePersonalDetailPage;
    public static DeleteEmployeePage deleteEmployeePage;

    public static void initPageElements() {

        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        addEmployeePage = new AddEmployeePage();
        addUsernamePage = new AddUsernamePage();
        addDependantsPage = new AddDependantsPage();
        addProfilePicturePage = new AddProfilePicturePage();
        searchEmployeePage = new SearchEmployeePage();
        updateContactDetailPage = new UpdateContactDetailPage();
        updatePersonalDetailPage = new UpdatePersonalDetailPage();
        deleteEmployeePage = new DeleteEmployeePage();
    }
}
