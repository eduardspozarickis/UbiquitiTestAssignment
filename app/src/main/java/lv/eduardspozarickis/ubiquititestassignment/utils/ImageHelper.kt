package lv.eduardspozarickis.ubiquititestassignment.utils

object ImageHelper {

    fun getImageUrl(id: String, imageIdentifier: String, size: Int): String {
        return "https://images.svc.ui.com/?u=https%3A%2F%2Fstatic.ui.com%2Ffingerprint%2Fui%2Fimages%2F$id%2Fdefault%2F$imageIdentifier.png&w=$size&q=75"
        // https://images.svc.ui.com/?u=https%3A%2F%2Fstatic.ui.com%2Ffingerprint%2Fui%2Fimages%2Fcdd9b268-13a2-404f-b295-9e8d0c605de0%2Fdefault%2F1435ef4d1b7caf065251ea2fcab512fb.png&w=128&q=75
    }
}