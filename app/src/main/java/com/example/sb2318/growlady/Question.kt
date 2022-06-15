package com.example.sb2318.growlady

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val name: String?, val option1: String?,
    val option2: String?, val option3: String?,
    val option4: String?, val answerNr: String?
):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(option1)
        dest?.writeString(option2)
        dest?.writeString(option3)
        dest?.writeString(answerNr)
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}