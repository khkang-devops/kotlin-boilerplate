<template>
  <div>
    <h1>샘플 리스트</h1>

    <!-- 로딩 상태 표시 -->
    <div v-if="loading">로딩 중...</div>

    <!-- 에러 메시지 표시 -->
    <div v-if="error" class="error">{{ error }}</div>

    <!-- 데이터 테이블 -->
    <table v-if="samples.length > 0">
      <thead>
      <tr>
        <th>ID</th>
        <th>이름</th>
        <!-- 필요한 다른 컬럼들 추가 -->
      </tr>
      </thead>
      <tbody>
      <tr v-for="sample in samples" :key="sample.sampleId">
        <td>{{ sample.sampleId }}</td>
        <td>{{ sample.sampleName }}</td>
        <!-- 필요한 다른 데이터 필드들 추가 -->
      </tr>
      </tbody>
    </table>

    <!-- 데이터가 없을 때 메시지 -->
    <div v-if="!loading && samples.length === 0">
      데이터가 없습니다.
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Hello',
  data() {
    return {
      samples: [],
      loading: false,
      error: null
    }
  },
  created() {
    this.fetchSamples()
  },
  methods: {
    async fetchSamples() {
      this.loading = true
      this.error = null

      try {
        const response = await axios.post('/api/samples/list', {
          // SampleRequestDto에 필요한 파라미터 추가
        })

        if (response.data.code === '0000') {
          this.samples = response.data.data
        } else {
          this.error = response.data.message || '데이터를 불러오는데 실패했습니다.'
        }
      } catch (e) {
        this.error = '서버 오류가 발생했습니다.'
        console.error('Error fetching samples:', e)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.error {
  color: red;
  margin: 10px 0;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f5f5f5;
}
</style>
