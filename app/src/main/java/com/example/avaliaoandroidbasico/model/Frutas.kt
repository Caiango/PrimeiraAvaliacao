package com.example.avaliaoandroidbasico.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Frutas(var nome: String?, var foto: String?, var desc: String?) : Parcelable