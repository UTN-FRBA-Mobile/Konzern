package utn.frba.mobile.konzern.posts.repository

class NewsRepository: BasePostRepository() {
    override var dbCollectionName = "news"
}