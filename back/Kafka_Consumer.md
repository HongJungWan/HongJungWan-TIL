# Kafka Consumer

### [Kafka 공식 문서](https://kafka.apache.org/)

<br>

<img src="../image/kafka-3/kafka-3-1.png" width="800">

<br>

### Properties 설정

Properties 객체를 생성하여 Kafka 서버와 관련된 설정을 한다.

<br>

### `bootstrap.servers`

Kafka 클러스터에 연결하기 위한 주소다. 

여기서는 "localhost:9092"로 로컬에서 실행 중인 Kafka 서버에 연결한다.

<br>

### `group.id`

Consumer Group의 ID를 설정한다. 

Consumer Group은 여러 Consumer 인스턴스를 묶어 처리량을 확장하거나 메시지를 분배하는 데 사용된다.

<br>

### `key.deserializer와 value.deserializer`

Kafka 메시지는 키와 값의 쌍으로 구성된다. 

이들은 Kafka로부터 메시지를 수신할 때 어떻게 직렬화 해제할지 결정. 

위에서는 문자열로 직렬화 해제하는 StringDeserializer를 사용.

<br><br>

### KafkaConsumer 생성

위에서 정의한 properties를 사용하여 KafkaConsumer 객체를 생성한다.

<br>

### `consumer.subscribe()`

Consumer가 메시지를 수신할 토픽을 구독한다. 위 예제에서는 "simple"이라는 토픽을 구독한다.

<br>

### `메시지 수신`

while 루프 내에서 consumer.poll() 메서드를 사용하여 Kafka로부터 메시지를 수신한다.

poll() 메서드는 지정된 시간 동안 메시지를 대기하며, 여기서는 100밀리초 동안 대기한다.

수신된 각 메시지(레코드)에 대해, 그 내용을 출력한다. 여기서는 메시지의 값, 토픽, 파티션 번호, 오프셋을 출력한다.

<br>

### `consumer.close()`

작업이 끝나면 Consumer를 종료한다.

<br>

이 코드는 Kafka에서 메시지를 수신하는 기본적인 방법을 보여준다.

<br><br><br>

<img src="../image/kafka-3/kafka-3-2.png" width="800">

<br>

### 1️⃣

Consumer Group A 내에는 Consumer1 하나만 존재하며, 이 Consumer는 두 개의 파티션 모두에서 메시지를 수신하고 있다.

<br>

### 2️⃣ 

파티션은 이제 두 Consumer 사이에 균등하게 분배된다. Consumer1은 topic1 - p0 파티션에서, Consumer2는 topic1 - p1 파티션에서 메시지를 수신한다.

<br>

### 3️⃣

주의할 점은 현재 토픽 topic1에는 파티션이 2개만 있기 때문에, 한 Consumer는 파티션을 할당받지 못한다. 따라서 Consumer1과 Consumer2는 각각 하나의 파티션에서 메시지를 계속 수신하며, Consumer3는 파티션을 할당받지 못해 메시지를 수신하지 않는다.

<br><br><br>

<img src="../image/kafka-3/kafka-3-3.png" width="800">

<br>

위 이미지는 카프카 Consumer의 동작 원리 중, 오프셋 관리와 메시지 가져오기의 과정을 간략히 나타내고 있다. 

poll 호출을 통해 메시지를 가져올 때마다, Consumer는 어디까지 메시지를 읽었는지(이전 커밋 오프셋), 그리고 어디까지 메시지를 읽을 수 있는지(오프셋 커밋)를 업데이트하며 동작한다.

<br><br><br>

<img src="../image/kafka-3/kafka-3-4.png" width="800">

<br>

위 이미지는 카프카(Kafka)에서의 Consumer 동작 방식 중 오프셋 초기화 전략(auto.offset.reset 속성)에 관한 내용을 설명.

<br>

### auto.offset.reset 설정 옵션

<br>

### `earliest`

가장 초기의 오프셋부터 메시지를 시작하여 가져온다. 위 이미지에서는 0이라는 위치로 표시되어 있다. 이 설정은 Consumer가 처음 시작될 때나, 저장된 오프셋이 더 이상 유효하지 않은 경우(예: 해당 오프셋의 메시지가 이미 삭제된 경우) 사용된다.

<br>

### `latest`

가장 최근의 오프셋부터 메시지를 가져온다. 이는 Consumer가 동작 중일 때 새로운 메시지만 가져오고 싶을 때 사용한다.

<br>

### `none`

이 설정은 저장된 오프셋이 없을 때 예외를 발생시킵다. 즉, 저장된 오프셋 정보가 없거나 유효하지 않은 경우 Consumer는 동작을 중지하게 된다.

<br><br>

위 설정은 Consumer가 메시지를 가져오기 시작하는 위치를 결정하는 중요한 역할을 한다. 예를 들어, earliest를 설정하면 Consumer는 토픽의 시작부터 모든 메시지를 가져오게 되며, latest를 설정하면 Consumer는 가장 최근에 추가된 메시지만 가져오게 된다. 

이러한 선택은 애플리케이션의 요구사항과 데이터 처리 전략에 따라 결정된다.

<br><br><br>

<img src="../image/kafka-3/kafka-3-5.png" width="800">

<br>

위 이미지는 카프카(Kafka) Consumer의 데이터 가져오기(fetch) 관련 설정에 대해 설명하고 있다.

<br>

### `fetch.min.bytes`

Consumer가 데이터를 가져오기 시작하기 위해 필요한 최소 바이트 수다.

`기본값: 1` : 이는 Consumer가 최소 1바이트의 데이터만 있어도 데이터를 가져올 수 있다는 것을 의미한다.

`주요 포인트`: 기본값 설정을 높게 하면, Consumer는 더 큰 양의 데이터가 쌓일 때까지 기다린 다음 가져오게 된다. 이같은 방법은 네트워크 I/O를 줄이고 효율성을 높이기 위한 전략으로 사용될 수 있다.

<br>

### `fetch.max.wait.ms`

Consumer가 fetch.min.bytes 설정에 따른 최소 바이트 수를 충족하기 위해 기다리는 최대 시간이다.

`기본값: 500 밀리초(ms)`

`주요 포인트`: 만약 설정된 시간 동안 fetch.min.bytes에 지정된 바이트 수가 충족되지 않더라도, 해당 시간이 지나면 Consumer는 현재 사용 가능한 데이터를 가져오게 된다.

<br>

### `max.partition.fetch.bytes`

Consumer가 한 번의 poll() 요청으로 각 파티션에서 가져올 수 있는 최대 바이트 수다.

`기본값: 1048576 바이트, 즉 1MB`

`주요 포인트` : 이 설정은 각 파티션에서 가져오는 데이터의 크기를 제한한다. 이를 통해 Consumer가 너무 많은 데이터를 한 번에 처리하는 것을 방지하며, 메모리 사용량을 관리할 수 있다.

<br>

이 설정들은 카프카 Consumer의 성능과 효율성을 최적화하기 위해 중요하다.

<br><br><br>

<img src="../image/kafka-3/kafka-3-6.png" width="800">

<br>

<br><br><br>

<img src="../image/kafka-3/kafka-3-7.png" width="800">

<br>

<br><br><br>

<img src="../image/kafka-3/kafka-3-8.png" width="800">

<br>

<br><br><br>

<img src="../image/kafka-3/kafka-3-9.png" width="800">

<br>

<br><br><br>

<img src="../image/kafka-3/kafka-3-10.png" width="800">

<br>

<br><br><br>

<img src="../image/kafka-3/kafka-3-11.png" width="800">

<br>

<br><br><br>