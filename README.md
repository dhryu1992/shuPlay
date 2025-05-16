# ShuPlay 🎧  
Jetpack Compose 기반의 오디오/팟캐스트 앱입니다.  
최신 Android 아키텍처를 적용하여 MVVM + Clean 구조로 구성되어 있으며,  
재생/저장/구독 등 기본 기능을 제공합니다.

---

## 📱 주요 기능
- 에피소드 리스트 조회
- 팟캐스트 재생 및 일시정지
- 즐겨찾기 등록/해제
- 최근 재생 에피소드 표시
- UI: Jetpack Compose 기반

| 홈 화면 | 재생 화면 |
|---------|----------|
| ![home](screenshots/home.png) | ![player](screenshots/player.png) |

---

## 🔧 기술 스택
- Kotlin
- Jetpack Compose
- Hilt (DI)
- Room (Local DB)
- Retrofit (API 통신)
- Coroutine + Flow

---

## 🧱 아키텍처
- MVVM + Clean Architecture
- 각 계층 분리: `domain`, `data`, `presentation`
- ViewModel에서 상태 관리 (StateFlow)
- DI: Hilt로 모듈 구성

---

## 📦 프로젝트 실행
1. Git clone  
   ```bash
   git clone https://github.com/dhryu1992/shuPlay.git
