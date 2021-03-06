package com.template.telegramm.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.template.telegramm.R
import com.template.telegramm.model.CommonModel
import com.template.telegramm.ui.fragments.single_chat.SingleChatFragment
import com.template.telegramm.utillits.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, ContactsHolder>
    private lateinit var mRefContacts: DatabaseReference//ссылка откуда будем скачивать данные
    private lateinit var mRefUsers: DatabaseReference
    private lateinit var mRerUsersListener: AppValueEventListener
    private lateinit var mapListeners: HashMap<DatabaseReference, AppValueEventListener>

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Contacts"
        initRecyclerView()

    }

    private fun initRecyclerView() {
        mRecyclerView = contacts_recycle_view
        mRefContacts = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()
        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, ContactsHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)

                mRerUsersListener = AppValueEventListener {
                    mRefUsers.addValueEventListener(AppValueEventListener {
                        val contact = it.getCommonModel()
                        if(contact.fullname.isEmpty()) {
                            holder.name.text = model.fullname
                        } else holder.name.text = contact.fullname

                        holder.status.text = contact.state
                        holder.photo.downloadAndSetImage(contact.photoUrl)
                        //установим на каждый контакт слушатель кликов
                        holder.itemView.setOnClickListener {
                            replaceFragment(SingleChatFragment(model))
                        }
                    })
                    mRefUsers.addValueEventListener(mRerUsersListener)
                    mapListeners[mRefUsers] = mRerUsersListener
                }


            }
        }
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }


    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.contact_fullname
        val status: TextView = view.contact_status
        val photo: CircleImageView = view.contact_photo
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        println()
        mapListeners.forEach{
            it.key.removeEventListener(it.value)
        }
        println()
    }
}


