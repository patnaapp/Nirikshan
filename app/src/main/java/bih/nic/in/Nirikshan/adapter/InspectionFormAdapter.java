package bih.nic.in.Nirikshan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import bih.nic.in.Nirikshan.entity.InspectionFormModel;
import bih.nic.in.fieldinspection.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspectionFormAdapter extends RecyclerView.Adapter<InspectionFormAdapter.ViewHolder> {
    private final Context mctx;
    private final ArrayList<InspectionFormModel> inspectionFormModels;

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
        holder.bind(inspectionFormModels.get(position));

    }

    @Override
    public int getItemCount() {
        return inspectionFormModels.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_pacs_approved, ll_use_status;
        TextView tv_textview;

        EditText et_edittext;
        Spinner spn_spinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            et_edittext = (EditText) itemView.findViewById(R.id.et_edittext);
            spn_spinner = (Spinner) itemView.findViewById(R.id.spn_spinner);
            tv_textview = (TextView) itemView.findViewById(R.id.tv_textview);

            et_edittext.setVisibility(View.GONE);
            spn_spinner.setVisibility(View.GONE);


        }

        public void bind(InspectionFormModel equipmentOrderByFarmer) {
            if (equipmentOrderByFarmer != null) {

                tv_textview.setText(equipmentOrderByFarmer.getOption_Name());

                if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("T")) {
                    et_edittext.setVisibility(View.VISIBLE);
                    spn_spinner.setVisibility(View.GONE);
                } else if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("D1")) {
                    et_edittext.setVisibility(View.GONE);
                    spn_spinner.setVisibility(View.VISIBLE);
                } else if (equipmentOrderByFarmer.getControl_ID().equalsIgnoreCase("D2")) {
                    et_edittext.setVisibility(View.GONE);
                    spn_spinner.setVisibility(View.VISIBLE);
                }


            }
        }


    }


}
