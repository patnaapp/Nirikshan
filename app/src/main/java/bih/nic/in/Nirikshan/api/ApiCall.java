package bih.nic.in.Nirikshan.api;

import android.util.Log;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.entity.AppDetailsResponse;
import bih.nic.in.Nirikshan.entity.CommiteeDetailsModel;
import bih.nic.in.Nirikshan.entity.GetCommitteList;
import bih.nic.in.Nirikshan.entity.GetControlResponse;
import bih.nic.in.Nirikshan.entity.GetInspectionFormResponse;
import bih.nic.in.Nirikshan.entity.UserLogin;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ApiCall {
    private static final String ROOT_URL = "http://10.133.20.157/Api/";
    //private static final String ROOT_URL = "http://192.168.217.111/Nirikshan_API/Api/";





    public final static String AUTH_BASIC = "Authorization";
    public static ApiSingleCall apiSingleCall = null;

    public static ApiSingleCall getSeervice() {
        if (apiSingleCall == null) {
            try {//Create ApiSingleCall
                Retrofit retrofitBase = new Retrofit.Builder()
                        .baseUrl(ROOT_URL)
                        //.addConverterFactory(GsonConverterFactory.create(gson))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiSingleCall = retrofitBase.create(ApiSingleCall.class);
            } catch (Exception e) {
                Log.e("MuRavi:", "Exception: ", e.getCause());
            }
        }
        return apiSingleCall;
    }

    public interface ApiSingleCall {


        @GET("GetAppDetails")
        Call<AppDetailsResponse> getAppDetails();

        @POST("Authenticate")
        Call<UserLogin> getUserLogin(@Body UserLogin userLogin);

        @POST("GetCommitteeList")
        Call<ArrayList<GetCommitteList>> getLoadCommitteList(@Body GetCommitteList getCommitteList);


        @POST("GetCommitteeDetails")
        Call<CommiteeDetailsModel> getCommitteDetails(@Body CommiteeDetailsModel commiteeDetailsModel);

        @POST("GetSubUnitOptionList")
        Call<GetInspectionFormResponse> getInspectionDetails(@Body GetInspectionFormResponse getInspectionFormResponse);

        @POST("GetControlList")
        Call<GetControlResponse> getControlDetails(@Body GetControlResponse getInspectionFormResponse);



    }
}
