package bih.nic.in.Nirikshan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.nic.in.Nirikshan.activity.CommitteeSelectionActivity;
import bih.nic.in.Nirikshan.databasehelper.DataBaseHelper;
import bih.nic.in.Nirikshan.entity.ControlModel;
import bih.nic.in.Nirikshan.entity.GetCommitteList;
import bih.nic.in.Nirikshan.entity.InspectionFormModel;
import bih.nic.in.fieldinspection.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspectionFormAdapter extends RecyclerView.Adapter<InspectionFormAdapter.ViewHolder> {
    private final Context mctx;
    private final ArrayList<InspectionFormModel> inspectionFormModels;
    DataBaseHelper dataBaseHelper;
    ArrayList<ControlModel> ControlList = new ArrayList<ControlModel>();
    Spinner spn_spinner;
    ArrayList<String> controlNameArray;
    ArrayAdapter<String> controladapter;

    public InspectionFormAdapter(Context mctx, ArrayList<InspectionFormModel> inspectionFormModelArrayList) {
        this.mctx = mctx;
        this.inspectionFormModels = inspectionFormModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.insp_form_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InspectionFormModel form=inspectionFormModels.get(position);
        holder.bind(form);
    }

    @Override
    public int getItemCount() {
        return inspectionFormModels.size();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_pacs_approved, ll_use_status;
        TextView tv_textview;

        EditText et_edittext;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            et_edittext = itemView.findViewById(R.id.et_edittext);
            spn_spinner = itemView.findViewById(R.id.spn_spinner);
            tv_textview = itemView.findViewById(R.id.tv_textview);
        }

        public void bind(InspectionFormModel equipmentOrderByFarmer) {
            if (equipmentOrderByFarmer != null) {

                tv_textview.setText(equipmentOrderByFarmer.getOption_Name());

                et_edittext.setVisibility(View.GONE);
                spn_spinner.setVisibility(View.GONE);

                if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("T1")) {
                    et_edittext.setVisibility(View.VISIBLE);
                    spn_spinner.setVisibility(View.GONE);
                } else if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("T2")) {
                    et_edittext.setVisibility(View.VISIBLE);
                    spn_spinner.setVisibility(View.GONE);
                } else if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("D1")) {
                    et_edittext.setVisibility(View.GONE);
                    spn_spinner.setVisibility(View.VISIBLE);
                    loadSpinner("D1");
                } else if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("D2")) {
                    et_edittext.setVisibility(View.GONE);
                    spn_spinner.setVisibility(View.VISIBLE);
                    loadSpinner("D2");

                }else{

                }


            }
        }


    }
    public void loadSpinner(String id) {
        dataBaseHelper=new DataBaseHelper(mctx);

        ControlList = dataBaseHelper.getDropDownList(id);
        controlNameArray = new ArrayList<String>();
        controlNameArray.add("-Select-");
        int i = 1;
        for (ControlModel district : ControlList) {
            controlNameArray.add(district.getValue());
            i++;
        }
        controladapter = new ArrayAdapter<String>(mctx,android.R.layout.simple_spinner_dropdown_item, controlNameArray);
        spn_spinner.setAdapter(controladapter);

    }


}
