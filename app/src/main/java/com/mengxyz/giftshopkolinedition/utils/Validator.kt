package com.mengxyz.giftshopkolinedition.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

private fun validate(pwd: TextInputEditText, cPwd: TextInputEditText):Boolean{
    return pwd.text.toString() == cPwd.text.toString()
}

class Validator {

    class Password(
        private val pwd:TextInputEditText,
        private val pwdLayout: TextInputLayout,
        private val cPwd: TextInputEditText? = null,
        private val cPwdLayout: TextInputLayout? = null
    ):TextWatcher{

        private fun isValidate(str:String) : Boolean{
            return str.length >= 6
        }

        override fun afterTextChanged(s: Editable?) {
            if (pwd.text.toString().isNotEmpty()){
                if(isValidate(pwd.text.toString())){
                    pwdLayout.isErrorEnabled = false
                }else{
                    pwdLayout.error = "Password min 6 character"
                }
            }else{
                pwdLayout.error = "Password required"
            }

            // matches password
            if(cPwd != null && cPwdLayout != null){
                if(!validate(pwd,cPwd)){
                    cPwdLayout.error = "Password not match"
                }else{
                    cPwdLayout.isErrorEnabled = false
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    class Email(
        private val email: TextInputEditText,
        private val emailLayout: TextInputLayout
    ) : TextWatcher {

        private fun isEmailValid(email: CharSequence): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        override fun afterTextChanged(s: Editable?) {
            if (email.text.toString().isEmpty()) {
                emailLayout.error = "Email is required"
            } else if (!isEmailValid(email.text.toString())) {
                emailLayout.error = "Email is required"
            } else {
                emailLayout.isErrorEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    class ConfirmPassword(
        private val pwd:TextInputEditText,
        private val cPwd:TextInputEditText,
        private val cPwdLayout: TextInputLayout
    ):TextWatcher{


        override fun afterTextChanged(s: Editable?) {
            if(!validate(pwd,cPwd)){
                cPwdLayout.error = "Password not match"
            }else{
                cPwdLayout.isErrorEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

}