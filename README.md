# UI-reference

### 1. Canvas 이용한 도넛 차트


![Alt Text](https://media.giphy.com/media/mKC1PBYotnHLt7k5vl/giphy.gif)

#### 구현방법

미션 리스트 갱신 시, DonutView 에 리스트 전달 후 도넛을 그립니다.

1. 미션 리스트 갱신 시, DonutView에 리스트 전달
```kotlin
viewModel.missionList.observe(viewLifecycleOwner, {
    ...
    binding.donutView.missionItems = it
})
```

2. DonutView의 미션 리스트를 set 할 시에, 도넛차트를 그리기 위한 paint 목록 allocation 및 draw 수행
```kotlin
var missionItems = listOf<MissionItem>()
    set(items) {
        field = items
        for (item in items) {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = item.color
            }
            itemPaints.put(item.missionId, paint)
        }
        invalidate()
    }
```
3. DonutView onSizeChanged 에서 도넛 사이즈 설정
```kotlin
override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    radius = (w/2).toFloat() // 기본 도넛 형태를 위한 바깥원 크기
    innerRadius = ((h/4).toFloat()) // 도넛 형태를 위한 내부원 크기

    oval = RectF(0f, 0f, width.toFloat(), height.toFloat()) // drawArc 로 도넛차트를 그리기 위한 원 크기
}
```
4. DonutView onDraw 에서 원 및 호 그리기
```kotlin
override fun onDraw(canvas: Canvas) {
    // 원 중심 coordinate
    val cx = (width/2).toFloat()
    val cy = (height/2).toFloat()

    canvas.run {
        drawCircle(cx, cy, radius, paint) // 기본 바깥 원 그리기

        val totalAmount = getTotalAmount(missionItems) // 비율 계산을 위한 총합 계산
        var lastAngle = 0f // 호를 이어서 그리기 위한 최근 angle 저장
        for (missionItem in missionItems) {
            val ratioAngle = (missionItem.amount.toFloat() / totalAmount)*360f
            val paint = itemPaints.get(missionItem.missionId)
            drawArc(oval, lastAngle, ratioAngle, true, paint!!)
            lastAngle += ratioAngle
        }

        drawCircle(cx, cy, innerRadius, innerPaint)
    }
}
```
