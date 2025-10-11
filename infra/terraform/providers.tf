variable "manifests_dir" {
  description = "Directory that contains Kubernetes YAML manifests"
  type        = string
  default     = "../k8s"
}

provider "kubectl" {
  # By default provider will look for KUBECONFIG env var or ~/.kube/config
  # Leave empty to use defaults from environment
}
