# App_Food
# Duy_Cong
# duycong998
 listCommentator.addAll(
            listOf(
                Commentator(
                    User(
                        "1",
                        "2",
                        "https://vnn-imgs-a1.vgcloud.vn/icdn.dantri.com.vn/2019/10/21/nu-sinh-bac-ninh-mac-dong-phuc-hut-anh-nhin-vi-nhan-sac-kha-aidocx-1571614825913.jpeg",
                        "",
                        "",
                        "",
                        Role.USER,
                        Gender.FEMALE,
                        "",
                        ""
                    ), "1"
                )
            )
        )
        commentAdapter.updateCommentator(listCommentator)
      //  recycler_view_dialog_commentt.adapter = commentAdapter

        viewBinding.containerComment.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)

            val bottomSheetView = LayoutInflater.from(this).inflate(
                R.layout.layout_dialog_comment, findViewById(R.id.container_item_dialog)
            )
            bottomSheetDialog.setContentView(bottomSheetView)
            val dialogCommentBinding = LayoutDialogCommentBinding.inflate(layoutInflater)
            dialogCommentBinding.recyclerViewDialogComment.adapter = commentAdapter
            dialogCommentBinding.buttonAddDialogComment.setOnClickListener {
                Log.d("AAAAA", "CCCCCCCCC")

            }
            bottomSheetDialog.show()
            bottomSheetView.recycler_view_dialog_comment.adapter = commentAdapter
        }
