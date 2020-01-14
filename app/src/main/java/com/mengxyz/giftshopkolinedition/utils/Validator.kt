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
        pwd:TextInputEditText,
        pwdLayout: TextInputLayout,
        cPwd: TextInputEditText? = null,
        cPwdLayout: TextInputLayout? = null
    ):TextWatcher{

        private val mPwd = pwd
        private val mPwdLayout = pwdLayout
        private val mCPwd = cPwd
        private val mCPwdLayout = cPwdLayout

        private fun isValidate(str:String) : Boolean{
            return str.length >= 6
        }

        override fun afterTextChanged(s: Editable?) {
            if (mPwd.text.toString().isNotEmpty()){
                if(isValidate(mPwd.text.toString())){
                    mPwdLayout.isErrorEnabled = false
                }else{
                    mPwdLayout.error = "Password min 6 character"
                }
            }else{
                mPwdLayout.error = "Password required"
            }

            // matches password
            if(mCPwd != null && mCPwdLayout != null){
                if(!validate(mPwd,mCPwd)){
                    mCPwdLayout.error = "Password not match"
                }else{
                    mCPwdLayout.isErrorEnabled = false
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    class Email(
        edt: TextInputEditText,
        layout: TextInputLayout
    ) : TextWatcher {
        private val mEdt = edt
        private val mLayout = layout

        private fun isEmailValid(email: CharSequence): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        override fun afterTextChanged(s: Editable?) {
            if (mEdt.text.toString().isEmpty()) {
                mLayout.error = "Email is required"
            } else if (!isEmailValid(mEdt.text.toString())) {
                mLayout.error = "Email is required"
            } else {
                mLayout.isErrorEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    class ConfirmPassword(
        pwd:TextInputEditText,
        cPwd:TextInputEditText,
        cPwdLayout: TextInputLayout
    ):TextWatcher{
        private val mPwd = pwd
        private val mCPwd = cPwd
        private val mCPwdLayout = cPwdLayout


        override fun afterTextChanged(s: Editable?) {
            if(!validate(mPwd,mCPwd)){
                mCPwdLayout.error = "Password not match"
            }else{
                mCPwdLayout.isErrorEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

}