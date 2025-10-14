output "manifests_applied" {
  description = "List of manifest files applied by Terraform"
  value       = local.manifest_files
}

output "manifests_dir" {
  value = var.manifests_dir
}
