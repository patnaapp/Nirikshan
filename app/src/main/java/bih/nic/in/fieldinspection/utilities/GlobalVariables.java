package bih.nic.in.fieldinspection.utilities;

import android.location.Location;


public class GlobalVariables {

    public static final String EDIT_PARAM = "User";
    public static final String RECORD_PARAM = "Details";
    public static boolean isOffline = false;
    public static boolean isOfflineGPS = false;
    public static int uploadNo=0;
    public static int listSize=0;
    public final static String SHARED_PREFERENCE_STRING = "bih.in.postmetric";
    public final static String USER_SHARED_PARAM = "userinfo";
    public final static String USER_PARAM = "UserInfoo";
    public final static String INS_PARAM = "UserInfoo";
    public final static String ARRAYLIST_PARAM = "arraylist";
    public final static String APPLICTIONID = "applicationId";
    public final static String PDF = "pdf";
    public final static String JPEG = "jpeg";
    public final static String APP_STATUS_URL = "Url";
    public final static String FINALIZED_PARAM = "finalized";
    public final static String APPLIED_PARAM = "Applied";
    public final static String COURCE_DETAILS_PARAM = "Applied";

    public static String REPORTTYPE="ReportType";
    public static int rtype=0;


    public static boolean fieldDownloaded=false;
    public static boolean SpinnerDataDownloaded=false;
    public static boolean downloadFyearData=false;
    public static boolean downloadDistrictData=false;


    public static String MunicipalCorporationId="";

    public static String WardId="";
    public static String AreaId="";
    public static String UserId="";
    public static String USER_NAME="_Username";
    public static String USER_ID="_Userid";
    public static String USER_PASSWORD="_Password";
    public static String USER_ROLE="_Role";
    public static String USER_F_YEAR="_FYear";
    public static String USER_MOBILE="_Mobiler";
    public static String USER_LAST_LOGIN="_LastLogin";
    public static String USER_IS_AUTH="_IsAuth";

    public static String ROLE_ADMIN="_RoleAdmin";
    public static String ROLE_STUDENT="_RoleStudent";
    public static String ROLE_INSTITUTE="_RoleInstitute";


    public static String INSTITUTE_NAME="INSTITUTE_NAME";
    public static String INSTITUTE_ID="INSTITUTE_ID";
    public static String INSTITUTE_CODE="INSTITUTE_CODE";
    public static String INSTITUTE_TIN="INSTITUTE_TIN";
    public static String INSTITUTE_TAN="INSTITUTE_TAN";
    public static String INSTITUTE_ADDRESS="INSTITUTE_ADDRESS";
    public static String DISTRICT_NAME="DISTRICT_NAME";
    public static String DISTRICT_CODE="DISTRICT_CODE";
    public static String STATE_NAME="STATE_NAME";
    public static String STATE_CODE="STATE_CODE";
    public static String STUDENT_DETAILS="StudentDetails";

    public static String INSTITUTE_TOKEN="INST_TOKEN";
    public static String IS_INSTITUTION_PROFILE_UPDATE="IsInstitutionProfileUpdate";
    public static String UPDATE="U";
    public static String INSERT="I";


    public static String Total_Course="Pending_Course";
    public static String Total_Student="Pending_Student";
    public static String Total_Institute="Pending_Institute";
    public static String Pending_Course="Pending_Course";
    public static String Pending_Student="Pending_Student";
    public static String Pending_Institute="Pending_Institute";
    public static String Approved_Course="Approved_Course";
    public static String Approved_Student="Approved_Student";
    public static String Approved_Institute="Approved_Institute";
    public static String Reject_Course="Reject_Course";
    public static String Reject_Student="Reject_Student";
    public static String Reject_Institute="Reject_Institute";
    public static String Defected_Course="Defected_Course";
    public static String Defected_Student="Defected_Student";
    public static String Defected_Institute="Defected_Institute";



    public static String Last_Visited="";
    public static Location glocation=null;


    public static String[] monthNameList={" ","January","February","March","April","May","June","July","August"
            ,"September","October","November","December"};
}
