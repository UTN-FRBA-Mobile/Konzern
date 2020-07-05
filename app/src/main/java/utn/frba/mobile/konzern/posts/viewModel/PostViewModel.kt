package utn.frba.mobile.konzern.posts.viewModel

import utn.frba.mobile.konzern.posts.repository.BasePostRepository
import utn.frba.mobile.konzern.posts.repository.PostRepository

class PostViewModel : BasePostViewModel() {
    override val repository: BasePostRepository = PostRepository()
    override val canEdit: Boolean
        get() = true
}
