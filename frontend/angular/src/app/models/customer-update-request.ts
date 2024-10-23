export interface CustomerUpdateRequest {
    name?: string
    email?: string
    age?: number
    gender?: 'MALE' | 'FEMALE'
}