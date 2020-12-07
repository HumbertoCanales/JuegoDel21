package com.example.juegodel21;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonaAdapter extends RecyclerView.Adapter<PersonaAdapter.PersonaHolder> {
    private List<Persona> personas;
    private LayoutInflater LI;

    public PersonaAdapter (List<Persona> personaList, Context context){
        this.LI=LayoutInflater.from(context);
        this.personas =personaList;
    }

    @Override
    public PersonaAdapter.PersonaHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=LI.inflate(R.layout.list_element,null);
        return new PersonaAdapter.PersonaHolder(view);
    }

    @Override
    public void onBindViewHolder(final PersonaAdapter.PersonaHolder holder, final int position){
        holder.bindData(personas.get(position));
    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public class PersonaHolder extends RecyclerView.ViewHolder{
        TextView nameTV;
        TextView ptTV;

        public PersonaHolder(View itemView){
            super(itemView);
            nameTV = itemView.findViewById(R.id.list_name);
            ptTV = itemView.findViewById(R.id.list_pt);
        }
        public void bindData(final Persona persona)
        {
            nameTV.setText(persona.getNombre());
            ptTV.setText(new StringBuilder().append("Puntos: ").append(persona.getNumero()).toString());
        }
    }
}
