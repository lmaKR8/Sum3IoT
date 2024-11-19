package com.example.compraventa.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.compraventa.Modelo.ModeloImagenSeleccionada
import com.example.compraventa.R
import com.bumptech.glide.Glide
import com.example.compraventa.databinding.ItemImagenesSeleccionadasBinding

class AdaptadorImagenSeleccionada (
    private val context: Context,
    private val imagenesSelecArrayList: ArrayList<ModeloImagenSeleccionada>,
    private val idAnuncio : String
) : Adapter<AdaptadorImagenSeleccionada.HolderImagenSeleccionada>() {
    private lateinit var binding: ItemImagenesSeleccionadasBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagenSeleccionada {
        binding = ItemImagenesSeleccionadasBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderImagenSeleccionada(binding.root)
    }

    override fun getItemCount(): Int {
        return imagenesSelecArrayList.size
    }

    override fun onBindViewHolder(holder: HolderImagenSeleccionada, position: Int) {
        val modelo = imagenesSelecArrayList[position]

        val imagenUri = modelo.imagenUri

        if (modelo.deInternet){
            try {
                Glide.with(context)
                    .load(imagenUri)
                    .placeholder(R.drawable.item_imagen)
                    .into(holder.item_imagen)
            }
            catch (e:Exception) {

            }
        }else{
            try {
                val imagenUri = modelo.imagenUri
                Glide.with(context)
                    .load(imagenUri)
                    .placeholder(R.drawable.item_imagen)
                    .into(holder.item_imagen)
            }catch (e:Exception){

            }
        }
    }


    inner class HolderImagenSeleccionada(itemView: View) : ViewHolder(itemView) {
        var item_imagen = binding.itemImagen
        var btn_cerrar = binding.cerrarItem
    }


}


